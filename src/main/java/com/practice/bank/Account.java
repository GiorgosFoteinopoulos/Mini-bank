package com.practice.bank;

import java.math.BigDecimal;

public class Account {

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("500.00");
    private static long nextAccountNumber = 1001;

    private final long accountNumber;
    private final String holder;
    private final String pin;
    private BigDecimal balance;
    private BigDecimal withdrawnToday;


    public Account(String holder, String pin, BigDecimal openingBalance) {
        if (holder == null || holder.isBlank()) {
            throw new IllegalArgumentException("Holder name is required");
        }
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits.");
        }
        if (openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }

        this.accountNumber = nextAccountNumber++;
        this.holder = holder;
        this.pin = pin;
        this.balance = openingBalance;
        this.withdrawnToday = new BigDecimal("0.00");
    }

    public Account(String holder, String pin) {
        this(holder, pin, new BigDecimal("0.00"));
    }

    public boolean deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        balance = balance.add(amount);
        return true;
    }

    public boolean checkPin(String attempt) {
        return pin.equals(attempt);
    }

    public boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (balance.compareTo(amount) < 0) {
            return false;
        }
        if (withdrawnToday.add(amount).compareTo(DAILY_LIMIT) > 0) {
            return false;
        }
        balance = balance.subtract(amount);
        withdrawnToday = withdrawnToday.add(amount);
        return true;
    }

    public boolean transferTo(Account target, BigDecimal amount) {
        if (target == null || target == this) {
            return false;
        }
        if (!this.withdraw(amount)) {
            return false;
        }
        target.deposit(amount);
        return true;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
    public String getHolder() {
        return holder;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public BigDecimal getRemainingDailyLimit() {
        return DAILY_LIMIT.subtract(withdrawnToday);
    }

    public void resetDailyLimit() {
        withdrawnToday = new BigDecimal("0.00");
    }

    @Override
    public String toString() {
        return "Account #" + accountNumber + " | " + holder + " | balance " + balance + " EUR";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account other)) return false;
        return this.accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(accountNumber);
    }
}
