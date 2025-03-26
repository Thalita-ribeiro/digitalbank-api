package com.digitalbankapi.domain.factories;

import com.digitalbankapi.application.contracts.AccountDTO;
import com.digitalbankapi.domain.entities.Account;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.validation.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AccountFactory {

    @Autowired
    private AccountValidator accountValidator;

    public Account createAccount(AccountDTO accountDTO, Client client) {
        return Account.builder()
                .number(accountValidator.generateAccountNumber())
                .agency(accountDTO.agency())
                .balance(BigDecimal.ZERO)
                .accountType(accountDTO.accountType())
                .client(client)
                .creationDate(LocalDateTime.now())
                .build();
    }
}
