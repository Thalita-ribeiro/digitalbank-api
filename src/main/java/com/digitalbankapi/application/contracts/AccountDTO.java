package com.digitalbankapi.application.contracts;

import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.domain.model.AccountType;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record AccountDTO(
        Long id,

        @NotNull(message = "O tipo de conta não pode ser nulo")
        AccountType accountType,

        @NotNull(message = "A agência não pode ser nula")
        String agency,

        @NotNull(message = "O número da conta não pode ser nulo")
        String number,

        @NotNull(message = "O cliente não pode ser nulo")
        ClientResponseDTO client) {
}