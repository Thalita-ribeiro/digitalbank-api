package com.digitalbankapi.application.service;

import com.digitalbankapi.application.contracts.dto.request.ClientRequestDTO;
import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.domain.exceptions.InvalidClientException;

import java.util.List;

public interface ClientService {

    ClientResponseDTO createClient(ClientRequestDTO clientDTO) throws InvalidClientException;

    List<ClientResponseDTO> getAllClients();

    ClientResponseDTO getClientById(Long id);

    void deleteClient(Long id);
}
