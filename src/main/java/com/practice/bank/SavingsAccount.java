package com.practice.bank;

import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("200.00");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.03");

    public SavingsAccount(String holder, String pin, BigDecimal openingBalance) {
        super(holder, pin, openingBalance);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public BigDecimal getBalance() {
        return getAvailableBalance();
    }

    @Override
    public BigDecimal getDailyLimit() {
        return DAILY_LIMIT;
    }

    @Override
    public BigDecimal getInterestRate() {
        return INTEREST_RATE;
    }
}
