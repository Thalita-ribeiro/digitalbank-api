package com.digitalbankapi.application.service.impl;

import com.digitalbankapi.application.contracts.AccountDTO;
import com.digitalbankapi.application.mappers.AccountMapper;
import com.digitalbankapi.application.service.AccountService;
import com.digitalbankapi.domain.entities.Account;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.exceptions.ResourceNotFoundException;
import com.digitalbankapi.domain.factories.AccountFactory;
import com.digitalbankapi.repository.AccountRepository;
import com.digitalbankapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFactory accountFactory;

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Client client = clientRepository.findById(accountDTO.client().id())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + accountDTO.client().id()));

        Account account = accountFactory.createAccount(accountDTO, client);

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toResponseDTO(savedAccount);
    }
}