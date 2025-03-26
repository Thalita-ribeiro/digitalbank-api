package com.digitalbankapi.application.mappers;

import com.digitalbankapi.application.contracts.AccountDTO;
import com.digitalbankapi.domain.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toResponseDTO(Account account);
}