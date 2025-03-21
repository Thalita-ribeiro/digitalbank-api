package com.digitalbankapi.application.contracts.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClientResponseDTO(Long id, String name) {
}