package com.practice.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface InterestBearing {

    BigDecimal getInterestRate();

    BigDecimal getBalance();

    default BigDecimal calculateYearlyInterest() {
        return getBalance().multiply(getInterestRate()).setScale(2, RoundingMode.HALF_EVEN);
    }
}
