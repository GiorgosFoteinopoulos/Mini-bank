package com.practice.bank;

import com.practice.bank.exception.AccountNotEmptyException;
import com.practice.bank.exception.AccountNotFoundException;

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

    private void register(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public CheckingAccount openCheckingAccount( String holder, String pin, BigDecimal openingBalance) {
        CheckingAccount account = new CheckingAccount(holder, pin, openingBalance);
        register(account);
        return account;
    }

    public SavingsAccount openSavingsAccount( String holder, String pin, BigDecimal openingBalance) {
        SavingsAccount account = new SavingsAccount(holder, pin, openingBalance);
        register(account);
        return account;
    }
    public void closeAccount(long accountNumber) {
        Account account = accounts.get(accountNumber);

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountNotEmptyException(accountNumber, account.getBalance());
        }
        accounts.remove(accountNumber);
    }

    public Account getAccount(long accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    public Map<Account, BigDecimal> applyYearlyInterest() {
        Map<Account, BigDecimal> payments = new LinkedHashMap<>();

        for (Account account : accounts.values()) {
            if (account instanceof InterestBearing saver) {
                BigDecimal interest = saver.calculateYearlyInterest();
                if (interest.compareTo(BigDecimal.ZERO) > 0) {
                    account.deposit(interest);
                    payments.put(account, interest);
                }
            }
        }
        return payments;
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
