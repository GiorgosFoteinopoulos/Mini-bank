package com.practice.bank;

import java.math.BigDecimal;
import java.sql.SQLOutput;

public class MiniBankApp {

    public static void main(String[] args) {

        System.out.println("Welcome to " + BankCalculator.bankName());

        double withDouble = 0.1 + 0.1 + 0.1;
        BigDecimal tenCents = new BigDecimal("0.1");
        BigDecimal withBigDecimal = tenCents.add(tenCents).add(tenCents);
        System.out.println("double:     " + withDouble);
        System.out.println("BigDecimal: " + withBigDecimal);
        System.out.println("From a double: " + new BigDecimal(0.1));

        BigDecimal balance = new BigDecimal("150.50");
        balance = BankCalculator.deposit(balance, new BigDecimal("49.50"));
        BankCalculator.printBalance("George", balance);

        System.out.println("Can withdraw 300? " + BankCalculator.canWithdraw(balance, new BigDecimal("300")));

        balance = BankCalculator.withdraw(balance, new BigDecimal("20"));
        BankCalculator.printBalance("George", balance);

        System.out.println("Interest at 0.025: " + BankCalculator.yearlyInterest(balance, new BigDecimal("0.025")));
        System.out.println("Interest at 2%:    " + BankCalculator.yearlyInterest(balance, 2));

        BigDecimal money = new BigDecimal("10");
        money.add(new BigDecimal("5"));
        System.out.println("C. Money: " + money);

        double myBalance = 50;
        BankCalculator.tryToAddBonus(myBalance);
        System.out.println("D. Outside the method: " + myBalance);

        BigDecimal a = new BigDecimal("2.0");
        BigDecimal b = new BigDecimal("2.00");
        System.out.println("E. equals: " + a.equals(b));
        System.out.println("E. compareTo: " + (a.compareTo(b) == 0));
    }
}