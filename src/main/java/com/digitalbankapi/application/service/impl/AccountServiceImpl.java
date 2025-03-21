package com.digitalbankapi.application.service.impl;

import com.digitalbankapi.application.contracts.AccountDTO;
import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.application.service.AccountService;
import com.digitalbankapi.domain.entities.Account;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.exceptions.ResourceNotFoundException;
import com.digitalbankapi.repository.AccountRepository;
import com.digitalbankapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final SecureRandom random = new SecureRandom();

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Client client = clientRepository.findById(accountDTO.client().id())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + accountDTO.client().id()));

        String accountNumber = generateAccountNumber();

        Account account = Account.builder()
                .number(accountNumber)
                .agency("0001")
                .balance(BigDecimal.ZERO)
                .accountType(accountDTO.accountType())
                .client(client)
                .creationDate(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);
        return mapToDTO(savedAccount);
    }

    private String generateAccountNumber() {
        return String.format("%06d", random.nextInt(1000000));
    }

    // Converte uma entidade Account para um objeto AccountDTO.
    //Usado para retornar dados ao cliente da API.

    private AccountDTO mapToDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .agency(account.getAgency())
                .number(account.getNumber())
                .client(mapToClientDTO(account.getClient()))
                .build();
    }

    private ClientResponseDTO mapToClientDTO(Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .build();
    }
}