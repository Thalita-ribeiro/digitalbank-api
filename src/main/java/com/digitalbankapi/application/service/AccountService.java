package com.digitalbankapi.application.service;

import com.digitalbankapi.application.contracts.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
}
