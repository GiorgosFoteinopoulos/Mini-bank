package com.practice.bank.exception;

import java.math.BigDecimal;

public class AccountNotEmptyException extends BankException {
    public AccountNotEmptyException(long accountNumber, BigDecimal balance) {

        super("Account " + accountNumber + " still holds" + balance + " EUR. It must be emptied before closing.");
    }
}
