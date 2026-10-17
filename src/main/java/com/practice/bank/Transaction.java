package com.practice.bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record Transaction(LocalDateTime timestamp,
                          TransactionType type,
                          BigDecimal amount,
                          BigDecimal balanceAfter,
                          String description) {

    public Transaction {
        Objects.requireNonNull(timestamp, "timestamp is required");
        Objects.requireNonNull(type, "type is required");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be zero or positive");
        }
    }
}
