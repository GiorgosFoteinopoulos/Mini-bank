package com.practice.bank.exception;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(long accountNumber) {

        super("No account with number " + accountNumber + ".");
    }
}
