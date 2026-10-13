package com.practice.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankCalculator {

    public static String bankName() {
        return "Mini Bank";
    }

    public static BigDecimal deposit(BigDecimal balance, BigDecimal amount) {
        return balance.add(amount);
    }

    public static boolean canWithdraw(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public static BigDecimal withdraw(BigDecimal balance, BigDecimal amount) {
        return balance.subtract(amount);
    }

    public static BigDecimal yearlyInterest(BigDecimal balance, BigDecimal rate) {
        return balance.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
    }
    public static BigDecimal yearlyInterest(BigDecimal balance, int  ratePercent) {
        BigDecimal rate = BigDecimal.valueOf(ratePercent).divide(BigDecimal.valueOf(100));
        return yearlyInterest(balance, rate);
    }

    public static BigDecimal monthlyInterest(BigDecimal balance, BigDecimal yearlyRate) {
        BigDecimal yearly = balance.multiply(yearlyRate);
        return yearly.divide(BigDecimal.valueOf(12));
    }

    public static void printBalance(String holder, BigDecimal balance) {
        System.out.println(holder + " has " + balance + " EUR");
    }

    public static void tryToAddBonus(double balance) {
        balance = balance + 100;
        System.out.println("Inside the method: " + balance);
    }
}
