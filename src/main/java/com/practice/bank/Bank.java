package com.practice.bank;

import java.math.BigDecimal;
import java.util.*;

public class Bank {

    private final String name;

    private final Map<Long, Account> accounts = new LinkedHashMap<>();

    public Bank(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Account openAccount(String holder, String pin, BigDecimal openingBalance) {
        Account account = new Account(holder, pin, openingBalance);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public boolean closeAccount(long accountNumber) {
        Account account = accounts.get(accountNumber);

        if (account == null) {
            return false;
        }
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        accounts.remove(accountNumber);
        return true;
    }

    public Account findAccount(long accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> findByHolder(String query) {
        List<Account> result = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Account account : accounts.values()) {
            if (account.getHolder().toLowerCase().contains(lowerQuery)) {
                result.add(account);
            }
        }
        return result;
    }

    public Collection<Account> getAllAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal getTotalDeposits() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account account : accounts.values()) {
            total = total.add(account.getBalance());
        }
        return total;
    }

    public void startNewDay() {
        for (Account account : accounts.values()) {
            account.resetDailyLimit();
        }
    }

    public Set<String> getHolderNames() {
        Set<String> names = new TreeSet<>();
        for (Account account : accounts.values()) {
            names.add(account.getHolder());
        }
        return names;
    }

    public Account findRichestAccount() {
        Account richest = null;

        for (Account account : accounts.values()) {
            if (richest == null || account.getBalance().compareTo(richest.getBalance()) > 0) {
                richest = account;

            }
        }
        return richest;
    }

}
