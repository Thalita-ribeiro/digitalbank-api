package com.digitalbankapi.application.service.impl;

import com.digitalbankapi.application.contracts.dto.request.ClientRequestDTO;
import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.application.service.ClientService;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.exceptions.InvalidAgeException;
import com.digitalbankapi.domain.factories.ClientFactory;
import com.digitalbankapi.domain.validation.AgeValidator;
import com.digitalbankapi.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientFactory clientFactory;
    @Autowired
    private AgeValidator ageValidator;

    @Override
    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientDTO) throws InvalidAgeException {
        if (!ageValidator.isAdult(clientDTO.dateOfBirth())) {
            log.warn("Cliente não é maior de idade: {}", clientDTO.dateOfBirth());
            throw new InvalidAgeException("Cliente deve ser maior de 18 anos para criar uma conta.");
        }

        Client client = clientFactory.createClient(clientDTO);
        Client savedClient = clientRepository.save(client);

        log.info("Cliente criado com sucesso: {}", savedClient);
        return mapToResponseDTO(savedClient);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ClientResponseDTO getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado"));
        return mapToResponseDTO(client);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente com ID " + id + " não encontrado");
        }
        clientRepository.deleteById(id);
        log.info("Cliente com ID {} excluído com sucesso", id);
    }

    private ClientResponseDTO mapToResponseDTO(Client client) {
        return new ClientResponseDTO(client.getId(), client.getName(), LocalDateTime.now());
    }
}
