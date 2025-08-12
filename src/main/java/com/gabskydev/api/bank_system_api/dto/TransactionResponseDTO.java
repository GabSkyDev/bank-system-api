package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        LocalDateTime timestamp,
        BigDecimal amount,
        TransactionType type,
        String description,
        String nameOrigin,
        String cpfOrigin,
        String nameDestination,
        String cpfDestination
) {}
