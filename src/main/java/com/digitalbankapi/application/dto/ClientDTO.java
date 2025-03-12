package com.digitalbankapi.application.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record ClientDTO(
        @NotBlank(message = "Nome não pode ser vazio")
        String name,

        @NotBlank(message = "CPF não pode ser vazio")
        @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas números")
        String cpf,

        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dateOfBirth
) {
}