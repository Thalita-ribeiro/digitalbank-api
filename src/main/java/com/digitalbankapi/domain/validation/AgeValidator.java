package com.digitalbankapi.domain.validation;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe para validar se a idade do cliente atende aos requisitos de negócio.
 */
@Component
public class AgeValidator {

    /**
     * Verifica se uma pessoa é maior de 18 anos.
     *
     * @param dateOfBirth Data de nascimento.
     * @return True se for maior ou igual a 18 anos, false caso contrário.
     */
    public boolean isAdult(LocalDate dateOfBirth) {
        int age = calculateAge(dateOfBirth);

        if (age > 18) {
            return true;
        }

        // Verifica se fez aniversário de 18 anos no ano atual.
        return age == 18 && hasBirthdayPassedThisYear(dateOfBirth);
    }

    /**
     * Calcula a idade em anos com base na data de nascimento.
     *
     * @param dateOfBirth Data de nascimento.
     * @return Idade em anos.
     */
    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Verifica se o aniversário de 18 anos já foi completado neste ano.
     *
     * @param dateOfBirth Data de nascimento.
     * @return True se o aniversário já foi completado, false caso contrário.
     */
    private boolean hasBirthdayPassedThisYear(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return today.getMonthValue() > dateOfBirth.getMonthValue()
                || (today.getMonthValue() == dateOfBirth.getMonthValue()
                && today.getDayOfMonth() >= dateOfBirth.getDayOfMonth());
    }
}