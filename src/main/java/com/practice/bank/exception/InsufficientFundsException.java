package com.practice.bank.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends BankException {


    private final BigDecimal available;
    private final BigDecimal requested;

    public InsufficientFundsException(BigDecimal available, BigDecimal requested) {

        super("Insufficient funds: available " + available + ", requested " + requested + ".");
        this.available = available;
        this.requested = requested;
    }

    public BigDecimal getAvailable() {
        return available;
    }
    public BigDecimal getRequested() {
        return requested;
    }
    public BigDecimal getShortfall() {
        return requested.subtract(available);
    }
}
