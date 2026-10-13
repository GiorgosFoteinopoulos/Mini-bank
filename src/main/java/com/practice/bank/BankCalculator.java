package com.practice.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankCalculator {

    public static String bankName() {
        return "Mini Bank";
    }

    public static BigDecimal yearlyInterest(BigDecimal balance, BigDecimal rate) {
        return balance.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
    }
    public static BigDecimal yearlyInterest(BigDecimal balance, int  ratePercent) {
        BigDecimal rate = BigDecimal.valueOf(ratePercent).divide(BigDecimal.valueOf(100));
        return yearlyInterest(balance, rate);
    }

    public static BigDecimal monthlyInterest(BigDecimal balance, BigDecimal yearlyRate) {
        BigDecimal yearly = balance.multiply(yearlyRate);
        return yearly.divide(BigDecimal.valueOf(12));
    }




}
