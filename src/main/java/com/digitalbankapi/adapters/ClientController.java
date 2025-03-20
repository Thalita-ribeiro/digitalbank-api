package com.digitalbankapi.adapters;

import com.digitalbankapi.application.contracts.dto.request.ClientRequestDTO;
import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.application.service.ClientService;
import com.digitalbankapi.domain.exceptions.InvalidAgeException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Validated ClientRequestDTO requestDTO) throws BadRequestException {
        try {
            var responseDTO = clientService.createClient(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid client data: {}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        } catch (InvalidAgeException e) {
            logger.error("Invalid age: {}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        var clients = clientService.getAllClients();

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        var client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
