package com.practice.bank;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {

    @Bean
    public Bank bank(@Value("${bank.name}") String name) {
        return new Bank(name);
    }
}
