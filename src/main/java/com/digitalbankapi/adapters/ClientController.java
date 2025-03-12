package com.digitalbankapi.adapters;

import com.digitalbankapi.application.dto.ClientDTO;
import com.digitalbankapi.application.service.ClientService;
import com.digitalbankapi.domain.entities.Client;
import com.digitalbankapi.domain.exceptions.InvalidAgeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Validated ClientDTO clientDTO) {
        try {
            Client client = clientService.createClient(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidAgeException e) {
            throw new RuntimeException(e);
        }
    }
}
