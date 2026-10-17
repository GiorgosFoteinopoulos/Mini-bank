package com.practice.bank;

public enum TransactionType {

    OPENING("Opening", true),
    DEPOSIT("Deposit", true),
    WITHDRAWAL("Withdrawal", false),
    TRANSFER_IN("Transfer in", true),
    TRANSFER_OUT("Transfer out", false),
    INTEREST("Interest", true);

    private final String label;
    private final boolean credit;

    TransactionType(String label, boolean credit) {
        this.label = label;
        this.credit = credit;
    }
    public String getLabel() {
        return label;
    }
    public boolean isCredit() {
        return credit;
    }
}
