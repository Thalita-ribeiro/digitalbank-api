package com.digitalbankapi.domain.factories;

import com.digitalbankapi.application.contracts.dto.request.ClientRequestDTO;
import com.digitalbankapi.domain.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientFactory {

    public Client createClient(ClientRequestDTO clientDTO) {
        return new Client(
                clientDTO.name(),
                clientDTO.cpf(),
                clientDTO.dateOfBirth(),
                clientDTO.email(),
                clientDTO.phoneNumber()
        );
    }
}