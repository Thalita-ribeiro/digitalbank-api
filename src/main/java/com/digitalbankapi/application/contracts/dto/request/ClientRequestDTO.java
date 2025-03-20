package com.digitalbankapi.application.contracts.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record ClientRequestDTO(
        @NotBlank(message = "Nome completo não pode ser vazio")
        String name,

        @NotBlank(message = "CPF não pode ser vazio")
        @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas números")
        String cpf,

        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dateOfBirth,

        @NotBlank(message = "Email não pode ser vazio")
        @Email
        String email,

        @NotBlank(message = "Telefone não pode ser vazio")
        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter entre 10 e 11 dígitos numéricos")
        String phoneNumber
) {
    public ClientRequestDTO {
        if (phoneNumber != null) {
            phoneNumber = phoneNumber.replaceAll("[\\-+\\s()]", "");
        }
    }
}
