package com.practice.bank.exception;

import java.math.BigDecimal;

public class InvalidAmountException extends BankException {
    public InvalidAmountException(BigDecimal amount) {
        super("Invalid amount: " + amount + ". The amount must be greater than 0.");
    }
}
