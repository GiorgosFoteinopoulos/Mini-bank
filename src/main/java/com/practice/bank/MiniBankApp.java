package com.practice.bank;

public class MiniBankApp {

    public static void main(String[] args) {
        String holderName = "George";
        long accountNumber = 1000_0000_0000L;
        int pinAttemptsLeft = 3;
        double balance = 150.50;
        boolean active = true;
        char accountType = 'S';
        final double interestRate = 0.02;

        System.out.println("=== Account ===");
        System.out.println("Holder: " + holderName);
        System.out.println("Number: " + accountNumber);
        System.out.println("Type: " + accountType);
        System.out.println("Active: " + active);
        System.out.println("PIN attempts left: " + pinAttemptsLeft);
        System.out.println("Balance: " + balance);


        double deposit = 49.50;
        balance = balance + deposit;
        System.out.println("After deposit: " + balance);

        balance -= 20;
        System.out.println("After withdrawing 20: " + balance);

        pinAttemptsLeft--;
        System.out.println("Wrong PIN! Attempts left: " + pinAttemptsLeft);


        double year1yInterest = balance * interestRate;

        int amount = 170;
        int notes = amount / 50;
        int leftover = amount % 50;
        System.out.println(amount + " = " + notes + " x 50 + " + leftover);

        double withdrawal = 300;
        boolean enoughMoney = balance >= withdrawal;
        boolean canWithdraw = active && enoughMoney;
        System.out.println("Enough money? + enoughMoney");
        System.out.println("Is savings" + (accountType == 'S'));

        double price = 199.99;
        int wholeEuros = (int) price;
        System.out.println("Whole euros: " + wholeEuros);

        System.out.println("Trap 1: " + 1 + 2);
        System.out.println("Trap 2: " + (1 + 2));
        System.out.println("Trap 3: " + 7 / 2);
        int biggest = Integer.MAX_VALUE;
        System.out.println("Trap 4: " + (biggest + 1));
        System.out.println("Trap 5: " + (0.1 + 0.2));
    }
}
