package com.practice.bank.exception;

import java.math.BigDecimal;

public class DailyLimitExceededException extends BankException {
    public DailyLimitExceededException(BigDecimal remaining, BigDecimal requested) {

        super("Daily limit exceeded. you can withdraw " + remaining + " more today, requested " + requested + ".");
    }
}
