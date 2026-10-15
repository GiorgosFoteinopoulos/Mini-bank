package com.practice.bank.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends BankException {


    private final BigDecimal balance;
    private final BigDecimal requested;

    public InsufficientFundsException(BigDecimal balance, BigDecimal requested) {

        super("Insufficient funds: balance " + balance + ", requested " + requested + ".");
        this.balance = balance;
        this.requested = requested;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public BigDecimal getRequested() {
        return requested;
    }
    public BigDecimal getShortfall() {
        return requested.subtract(balance);
    }
}
