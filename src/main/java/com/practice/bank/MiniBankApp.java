package com.practice.bank;

import com.practice.bank.exception.AccountNotFoundException;
import com.practice.bank.exception.BankException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;


public class MiniBankApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank("Mini Bank");
        bank.openAccount("George", "1234", new BigDecimal("100.00"));
        bank.openAccount("Anna", "5678", new BigDecimal("100.00"));
        bank.openAccount("Maria", "4321", new BigDecimal("100.00"));

        System.out.println("Welcome to " + bank.getName());
        System.out.println("(Demo: 1001 George/1234, 1002 Anna/5678, 1003 Maria/4321)");

        Account current = login(scanner, bank);
        if (current == null) {
            System.out.println("Card blocked. Please contact your branch.");
            return;
        }
        boolean running = true;
        int transactions = 0;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> System.out.println(current);
                    case "2" -> {
                        depositFlow(scanner, current);
                        transactions++;            // skipped automatically if depositFlow threw
                    }
                    case "3" -> {
                        withdrawFlow(scanner, current);
                        transactions++;
                    }
                    case "4" -> {
                        transferFlow(scanner, bank, current);
                        transactions++;
                    }
                    case "5" -> projectInterest(scanner, current);
                    case "6" -> openAccountFlow(scanner, bank);
                    case "7" -> listAccounts(bank);
                    case "8" -> searchFlow(scanner, bank);
                    case "9" -> {
                        bank.startNewDay();
                        System.out.println("A new day has started. Daily limits are reset.");
                    }
                    case "10" -> closeAccountFlow(scanner, bank, current);
                    case "0" -> running = false;
                    default  -> System.out.println("Unknown option: '" + choice + "'");
                }
            } catch (BankException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("You made " + transactions + " transaction(s) today.");
        System.out.println("Thank you for banking with " + bank.getName() + "!");
    }
    private static void printMenu() {
        System.out.println();
        System.out.println("=== MENU ===");
        System.out.println("1. My account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Interest projection");
        System.out.println("6. Open a new account");
        System.out.println("7. List all accounts");
        System.out.println("8. Search by holder name");
        System.out.println("9. Simulate a new day");
        System.out.println("10. Close an account");
        System.out.println("0. Exit");
        System.out.print("Choose:");
    }

    private static Account login(Scanner scanner, Bank bank) {
        while (true) {
            long number = readLong(scanner, "Account number: ");
            try {
                Account account = bank.getAccount(number);
                return checkPin(scanner, account) ? account : null;
            } catch (AccountNotFoundException e) {
                System.out.println(e.getMessage() + " Try again.");
            }
        }
    }


    private static void depositFlow(Scanner scanner, Account account) {
        BigDecimal amount = readAmount(scanner, "Amount to deposit: ");
       account.deposit(amount);
        System.out.println("Deposited " + amount + " New balance: " + account.getBalance());
    }

    private static void withdrawFlow(Scanner scanner, Account account) {
        System.out.println("You can withdraw up to " + account.getRemainingDailyLimit() + " more today.");
        BigDecimal amount = readAmount(scanner, "Amount to withdraw: ");
        account.withdraw(amount);
        System.out.println("Withdrawn " + amount + "New balance: " + account.getBalance());
    }

    private static void transferFlow(Scanner scanner, Bank bank, Account from) {
        long targetNumber = readLong(scanner, "Transfer to account number: ");
        Account target = bank.getAccount(targetNumber);
       BigDecimal amount = readAmount(scanner, "Amount to transfer to " + target.getHolder() + ":");
       from.transferTo(target, amount);
        System.out.println("Transferred " + amount + " to " + target.getHolder() + ".");
        System.out.println(from);
    }

    private static boolean checkPin(Scanner scanner, Account  account) {
        int attemptsLeft = 3;

        while (attemptsLeft > 0) {
            System.out.print("Enter PIN for " + account.getHolder() + ": ");
            String attempt = scanner.nextLine();

            if (account.checkPin(attempt)) {
                System.out.println("PIN accepted. Hello, " + account.getHolder() + "!");
                return true;
            }

            attemptsLeft--;
            System.out.println("Wrong PIN. Attempts left: " +  attemptsLeft);
        }
        return false;
    }

    private static void projectInterest(Scanner scanner, Account account) {
        int years = readPositiveInt(scanner, "How many years? ");
        BigDecimal rate = new BigDecimal("0.03");
        BigDecimal projected = account.getBalance();

        for (int year = 1; year <= years; year++) {
            BigDecimal interest = BankCalculator.yearlyInterest(projected, rate);
            projected = projected.add(interest);
            System.out.println("Year " + year + ": " + projected + "  (+" + interest + ")");
        }
    }

        private static void openAccountFlow(Scanner scanner, Bank bank) {
            System.out.print("Holder name: ");
            String holder = scanner.nextLine().trim();
            System.out.print("Choose 4-digit PIN: ");
            String pin = scanner.nextLine().trim();
            BigDecimal deposit = readAmount(scanner, "Opening deposit: ");

            try {
                Account account = bank.openAccount(holder, pin, deposit);
                System.out.println("Account opened: " + account);
        } catch (IllegalArgumentException e) {
                System.out.println("Could not open account" + e.getMessage());
            }
    }

    private static void listAccounts(Bank bank) {
        System.out.println("=== " + bank.getName() + ": " + bank.getNumberOfAccounts() + " accounts ===");
        for (Account account : bank.getAllAccounts()) {
            System.out.println(account);
        }
        System.out.println("Total deposits: " + bank.getTotalDeposits() + " EUR");
        System.out.println("Customers: " + bank.getHolderNames());

        Account richest = bank.findRichestAccount();
        if (richest != null) {
            System.out.println("Richest account: " + richest);
        } else {
            System.out.println("Richest account: none (the bank has no accounts with money).");
        }
    }

    private static void searchFlow(Scanner scanner, Bank bank) {
        System.out.print("Search holder name: ");
        String query = scanner.nextLine().trim();
        List<Account> found = bank.findByHolder(query);

        if (found.isEmpty()) {
            System.out.println("No accounts found for '" + query + "'.");
        } else {
            System.out.println(found.size() + " account(s) found:");
            for (Account account : found) {
                System.out.println(account);
            }
        }
    }

    private static long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a valid number.");
            }
        }
    }

        private static BigDecimal readAmount(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim().replace(",", ".");

            try {
                return new BigDecimal(input).setScale(2, RoundingMode.HALF_EVEN);

            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a valid amount. Example: 25.50");
            }
        }
        }

        private static void closeAccountFlow(Scanner scanner, Bank bank, Account current) {
        long number = readLong(scanner, "Account number to close: ");

        if (number == current.getAccountNumber()) {
            System.out.println("You cannot close the account are logged in with.");
            return;
        }

        bank.closeAccount(number);
            System.out.println("Account closed: " + current);
        }

        private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);

                if (number <= 0) {
                    System.out.println("Please enter a number greater than 0.");
                } else if (number > 50) {
                    System.out.println("Maximum is 50 years.");
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a whole number.");
            }
        }
        }
    }
