package com.practice.bank;

import com.practice.bank.exception.AccountNotEmptyException;
import com.practice.bank.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    public Optional<Account> findAccount(long accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public Account getAccount(long accountNumber) {
        return findAccount(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public Map<Account, BigDecimal> applyYearlyInterest() {
        Map<Account, BigDecimal> payments = new LinkedHashMap<>();

        for (Account account : accounts.values()) {
            if (account instanceof InterestBearing saver) {
                BigDecimal interest = saver.calculateYearlyInterest();
                if (interest.compareTo(BigDecimal.ZERO) > 0) {
                    account.addInterest(interest);
                    payments.put(account, interest);
                }
            }
        }
        return payments;
    }

    public List<Account> findByHolder(String query) {
        String lowerQuery = query.toLowerCase();
        return accounts.values().stream()
                .filter(account -> account.getHolder().toLowerCase().contains(lowerQuery))
                .toList();

    }

    public Collection<Account> getAllAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal getTotalDeposits() {
        return accounts.values().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void startNewDay() {
        accounts.values().forEach(Account::resetDailyLimit);
    }

    public Set<String> getHolderNames() {
        return accounts.values().stream()
                .map(Account::getHolder)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Optional<Account> findRichestAccount() {
        return accounts.values().stream()
                .max(Comparator.comparing(Account::getBalance));
    }

}
