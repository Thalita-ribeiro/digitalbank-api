package com.digitalbankapi.application.mappers;

import com.digitalbankapi.application.contracts.dto.response.ClientResponseDTO;
import com.digitalbankapi.domain.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDTO toResponseDTO(Client client);
}