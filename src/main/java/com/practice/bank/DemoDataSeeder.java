package com.practice.bank;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;


@Component
public class DemoDataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataSeeder.class);

    private final Bank bank;

    public DemoDataSeeder(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run(String... args) {
        bank.openCheckingAccount("George", "1234", new BigDecimal("150.00"));
        bank.openSavingsAccount("Anna", "5678", new BigDecimal("80.00"));
        bank.openSavingsAccount("Maria", "4321", new BigDecimal("300.00"));
        log.info("Demo data loaded: {} accounts", bank.getNumberOfAccounts());
    }
}
