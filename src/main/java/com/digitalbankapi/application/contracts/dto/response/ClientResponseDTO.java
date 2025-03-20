package com.digitalbankapi.application.contracts.dto.response;

import java.time.LocalDateTime;

public record ClientResponseDTO(Long id, String name, LocalDateTime createdAt) {
}