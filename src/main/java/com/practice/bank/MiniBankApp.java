package com.practice.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.util.Scanner;

public class MiniBankApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to " + BankCalculator.bankName());

        if (!checkPin(scanner)) {
            System.out.println("Card blocked. Please contact your branch.");
            return;
        }

        BigDecimal balance = new BigDecimal("150.00");
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> BankCalculator.printBalance("George", balance);
                case "2" -> balance = depositFlow(scanner, balance);
                case "3" -> balance = withdrawFlow(scanner, balance);
                case "4" -> projectInterest(scanner, balance);
                case "0" -> running = false;
                default -> System.out.println("Unknown option: '" + choice + "'");
            }
        }

        System.out.println("Thank you for banking with " + BankCalculator.bankName());
    }

    private static boolean checkPin(Scanner scanner) {
        final String correctPin = "1234";
        int attemptsLeft = 3;

        while (attemptsLeft > 0) {
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (pin.equals(correctPin)) {
                System.out.println("PIN accepter");
                return true;
            }

            attemptsLeft--;
            System.out.println("Wrong PIN. Attempts left: " +  attemptsLeft);
        }
        return false;
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== MENU =====");
        System.out.println("1. Show balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Interest projection");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static BigDecimal depositFlow(Scanner scanner, BigDecimal balance) {
        BigDecimal amount = readAmount(scanner, "Amount to deposit: ");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("The amount must be positive.");
            return balance;
        }
        BigDecimal newBalance = BankCalculator.deposit(balance, amount);
        System.out.println("Deposited " + amount + ". New balance: " + newBalance);
        return newBalance;
    }

    private static BigDecimal withdrawFlow(Scanner scanner, BigDecimal balance) {
        BigDecimal amount = readAmount(scanner, "Amount to withdraw: ");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("The amount must be positive.");
            return balance;
        } else if (!BankCalculator.canWithdraw(balance, amount)) {
            System.out.println("Insufficient funds. Your balance is " + balance);
            return balance;
        } else {
            BigDecimal newBalance = BankCalculator.withdraw(balance, amount);
            System.out.println("Withdrew " + amount + ". New balance: " + newBalance);
            return newBalance;
        }
    }

    private static void projectInterest(Scanner scanner, BigDecimal balance) {
        System.out.println("How many years? ");
        int years = Integer.parseInt(scanner.nextLine().trim());

        BigDecimal rate = new BigDecimal("0.03");
        BigDecimal projected = balance;

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
    }
