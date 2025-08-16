package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        Long id,
        @NotNull(message = "Amount cannot be blank")
        @DecimalMin(value = "0.01", inclusive = true, message = "The amount need to be higher than zero")
        BigDecimal amount,
        @NotNull(message = "Transaction type cannot be blank")
        TransactionType type,
        @Size(max = 255, message = "The description maximum size is 255 characters")
        String description,

        @Pattern(regexp = "\\d{11}", message = "The CPF origin need to have exactly 11 digits")
        String cpfOrigin,

        @Pattern(regexp = "\\d{11}", message = "The CPF destination need to have exactly 11 digits")
        String cpfDestination
) {
}
