package com.practice.bank;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

    private  static final BigDecimal DAILY_LIMIT = new BigDecimal("500.00");
    private static final BigDecimal OVERDRAFT_LIMIT = new BigDecimal("200.00");

    public CheckingAccount(String holder, String pin, BigDecimal openingBalance) {
        super(holder, pin, openingBalance);
    }

    public  CheckingAccount(String holder, String pin) {
        this(holder, pin, new BigDecimal("0.00"));
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    public BigDecimal getDailyLimit() {
        return DAILY_LIMIT;
    }

    @Override
    public BigDecimal getAvailableBalance() {
        return getBalance().add(OVERDRAFT_LIMIT);
    }

    public BigDecimal getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}
