package com.digitalbankapi.application.service.impl;

import com.digitalbankapi.application.contracts.dto.request.ClientRequestDTO;
import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.application.mappers.ClientMapper;
import com.digitalbankapi.application.service.ClientService;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.exceptions.InvalidClientException;
import com.digitalbankapi.domain.factories.ClientFactory;
import com.digitalbankapi.domain.validation.AgeValidator;
import com.digitalbankapi.domain.validation.ClientValidator;
import com.digitalbankapi.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.digitalbankapi.domain.shared.constants.ErrorMessages.CLIENT_NOT_FOUND;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientFactory clientFactory;
    @Autowired
    private AgeValidator ageValidator;
    @Autowired
    private ClientValidator clientValidator;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientDTO) throws InvalidClientException {
        clientValidator.validate(clientDTO);

        Client client = clientFactory.createClient(clientDTO);
        Client savedClient = clientRepository.save(client);

        log.info("Cliente criado com sucesso: {}", savedClient);
        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ClientResponseDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(CLIENT_NOT_FOUND));
        return clientMapper.toResponseDTO(client);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException(CLIENT_NOT_FOUND);
        }
        clientRepository.deleteById(id);
        log.info("Cliente com ID {} excluído com sucesso", id);
    }
}
