package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequestDTO(
        Long id,
        LocalDateTime timestamp,
        BigDecimal amount,
        TransactionType type,
        String description,
        String cpfOrigin,
        String cpfDestination
) {
}
