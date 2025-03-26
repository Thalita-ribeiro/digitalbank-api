package com.digitalbankapi.domain.validation;

import com.digitalbankapi.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountValidator {

    private final SecureRandom random = new SecureRandom();
    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.format("%06d", random.nextInt(1000000));
        } while (accountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }
}