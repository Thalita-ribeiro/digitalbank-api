package com.digitalbankapi.domain.validation;

import com.digitalbankapi.application.contracts.dto.request.ClientRequestDTO;
import com.digitalbankapi.domain.exceptions.InvalidClientException;
import com.digitalbankapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.digitalbankapi.domain.shared.constants.ErrorMessages.CLIENT_NOT_ADULT;
import static com.digitalbankapi.domain.shared.constants.ErrorMessages.CPF_ALREADY_EXISTS;

@Component
public class ClientValidator {

    private final AgeValidator ageValidator;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientValidator(AgeValidator ageValidator, ClientRepository clientRepository) {
        this.ageValidator = ageValidator;
        this.clientRepository = clientRepository;
    }

    public void validate(ClientRequestDTO clientDTO) throws InvalidClientException {
        validateAge(clientDTO);
        validateCPF(clientDTO);
    }

    private void validateAge(ClientRequestDTO clientDTO) throws InvalidClientException {
        if (!ageValidator.isAdult(clientDTO.dateOfBirth())) {
            throw new InvalidClientException(CLIENT_NOT_ADULT);
        }
    }

    private void validateCPF(ClientRequestDTO clientDTO) throws InvalidClientException {
        if (clientRepository.existsByCpf(clientDTO.cpf())) {
            throw new InvalidClientException(CPF_ALREADY_EXISTS);
        }
    }
}