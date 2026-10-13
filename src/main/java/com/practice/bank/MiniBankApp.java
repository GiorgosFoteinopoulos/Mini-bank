package com.practice.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.util.Scanner;

public class MiniBankApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to " + BankCalculator.bankName());

        Account george = new Account("George", new BigDecimal("150.00"));
        Account anna = new Account("Anna", new BigDecimal("80.00"));

        if (!checkPin(scanner)) {
            System.out.println("Card blocked. Please contact your branch.");
            return;
        }

        boolean running = true;
        int transactions = 0;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> System.out.println(george);
                case "2" -> {
                    if (depositFlow(scanner, george)) {
                        transactions++;
                    }
                }
                case "3" -> {
                    if (withdrawFlow(scanner, george)) {
                        transactions++;
                    }
                }
                case "4" -> {
                    if (transferFlow(scanner, george, anna)) {
                        transactions++;
                    }
                }
                case "5" -> projectInterest(scanner, george);
                case "6" -> {
                    System.out.println(george);
                    System.out.println(anna);
                }
                case "0" -> running = false;
                default -> System.out.println("Unknown option: '" + choice + "'");
            }
        }
        System.out.println("You made " + transactions + " transaction(s) today.");
        System.out.println("Thank you for banking with " + BankCalculator.bankName());
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== MENU ===");
        System.out.println("1. My account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer to Anna");
        System.out.println("5. Interest projection");
        System.out.println("6. Show all accounts");
        System.out.println("0. Exit");
    }

    private static boolean depositFlow(Scanner scanner, Account account) {
        BigDecimal amount = readAmount(scanner, "Amount to deposit: ");
        if (account.deposit(amount)) {
            System.out.println("Deposited " + amount + ". New balance: " + account.getBalance());
            return true;
        }
        System.out.println("Deposit refused: the amount must be positive.");
        return false;
    }

    private static boolean withdrawFlow(Scanner scanner, Account account) {
        System.out.println("You can withdraw up to " + account.getRemainingDailyLimit() + " more today.");
        BigDecimal amount = readAmount(scanner, "Amount to withdraw: ");
        if (account.withdraw(amount)) {
            System.out.println("Withdrew " + amount + ". New balance: " + account.getBalance());
            return true;
        }
        System.out.println("Withdrawal refused: check the amount, your balance and today's limit.");
        return false;
    }

    private static boolean transferFlow(Scanner scanner, Account from, Account to) {
        System.out.println("Transfer from " + from.getHolder() + " to " + to.getHolder());
        BigDecimal amount = readAmount(scanner, "Amount to transfer: ");
        if (from.transferTo(to, amount)) {
            System.out.println("Transferred " + amount + ".");
            System.out.println(from);
            System.out.println(to);
            return true;
        }
        System.out.println("Transfer refused: check the amount, your balance and today's limit.");
        return false;
    }

    private static boolean checkPin(Scanner scanner) {
        final String correctPin = "1234";
        int attemptsLeft = 3;

        while (attemptsLeft > 0) {
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (pin.equals(correctPin)) {
                System.out.println("PIN accepted");
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
