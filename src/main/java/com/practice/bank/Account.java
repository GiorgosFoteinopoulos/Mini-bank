package com.practice.bank;

import com.practice.bank.exception.BankException;
import com.practice.bank.exception.DailyLimitExceededException;
import com.practice.bank.exception.InsufficientFundsException;
import com.practice.bank.exception.InvalidAmountException;

import java.math.BigDecimal;

public abstract class Account {

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("500.00");
    private static long nextAccountNumber = 1001;

    private final long accountNumber;
    private final String holder;
    private final String pin;
    private BigDecimal balance;
    private BigDecimal withdrawnToday;


    protected Account(String holder, String pin, BigDecimal openingBalance) {
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

    public final void deposit(BigDecimal amount) {
        requirePositive(amount);
        balance = balance.add(amount);
    }

    public boolean checkPin(String attempt) {
        return pin.equals(attempt);
    }

    public final void withdraw(BigDecimal amount) {
        requirePositive(amount);

        BigDecimal available = getAvailableBalance();
        if (amount.compareTo(available) > 0) {
            throw new InsufficientFundsException(available, amount);
        }

        BigDecimal remaining = getRemainingDailyLimit();
        if (amount.compareTo(remaining) > 0) {
            throw new DailyLimitExceededException(remaining, amount);
        }
        balance = balance.subtract(amount);
        withdrawnToday = withdrawnToday.add(amount);
    }

    public final void transferTo(Account target, BigDecimal amount) {
        if (target == null || target == this) {
            throw new IllegalArgumentException("Target account is required.");
        }
        if (target == this) {
            throw new BankException("You can't transfer to your own account.");
        }
        withdraw(amount);
        target.deposit(amount);
    }

    private static void requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }
    }

    public long getAccountNumber() {
        return accountNumber;
    }
    public abstract String getAccountType();
    public String getHolder() {
        return holder;
    }
    public BigDecimal getBalance() {
        return balance;
    };
    public BigDecimal getAvailableBalance() {
        return balance;
    }
    public abstract BigDecimal getDailyLimit();
    public BigDecimal getRemainingDailyLimit() {
        return DAILY_LIMIT.subtract(withdrawnToday);
    }

    public void resetDailyLimit() {
        withdrawnToday = new BigDecimal("0.00");
    }

    @Override
    public String toString() {
        return "Account #" + accountNumber + " | " + getAccountType() + " | " + holder + " | balance " + balance + " EUR";
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
