package com.digitalbankapi.application.service;

import com.digitalbankapi.application.dto.ClientDTO;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.exceptions.InvalidAgeException;
import com.digitalbankapi.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

// Marca esta classe como um serviço Spring, que pode ser injetado em outros componentes
@Service
public class ClientService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClientService.class);

    // Injeta automaticamente o repositório de clientes nesta classe
    @Autowired
    private ClientRepository clientRepository;

    // Metodo para criar um novo cliente, marcado como transacional (tudo ou nada)
    @Transactional
    public Client createClient(ClientDTO clientDTO) throws InvalidAgeException {
        // Verifica se o cliente é maior de idade
        if (!isAdult(clientDTO.dateOfBirth())) {
            // Se não for adulto, lança uma exceção
            throw new InvalidAgeException("Cliente deve ser maior de 18 anos para criar uma conta");
        }

        // Cria uma nova instância de Cliente a partir dos dados do DTO
        Client client = new Client(
                clientDTO.name(),
                clientDTO.cpf(),
                clientDTO.dateOfBirth()
        );

        // Salva o cliente no banco de dados e retorna o cliente salvo
        return clientRepository.save(client);
    }

    // Método para verificar se uma pessoa é maior de idade baseado na data de nascimento
    public boolean isAdult(LocalDate dateOfBirth) {
        // Obtém a data atual
        LocalDate today = LocalDate.now();
        // Calcula a idade em anos
        int age = Period.between(dateOfBirth, today).getYears();

        // Se a idade for maior que 18, é adulto
        if (age > 18) {
            // Registra informação no log
            log.info("Cliente é maior de idade, pode criar conta");
            return true;
        } else if (age == 18) {
            // Se a idade for exatamente 18, verifica se já fez aniversário este ano
            boolean hasBirthdayPassed =
                    // Verifica se o mês atual é posterior ao mês de nascimento
                    (today.getMonthValue() > dateOfBirth.getMonthValue()) ||
                            // Ou se estamos no mesmo mês, verifica se o dia atual é igual ou posterior ao dia de nascimento
                            (today.getMonthValue() == dateOfBirth.getMonthValue() &&
                                    today.getDayOfMonth() >= dateOfBirth.getDayOfMonth());

            // Se já fez aniversário de 18 anos, é considerado adulto
            if (hasBirthdayPassed) {
                // Registra informação no log
                log.info("Cliente completou 18 anos, pode criar conta");
                return true;
            }
        }

        // Se chegou até aqui, o cliente não é maior de idade
        log.info("Cliente é menor de idade, não pode criar conta");
        return false;
    }
}