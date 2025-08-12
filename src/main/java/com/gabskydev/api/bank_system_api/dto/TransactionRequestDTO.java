package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record TransactionRequestDTO(
        Long id,
        @NotNull(message = "O valor da transação é obrigatório")
        @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior que zero")
        BigDecimal amount,
        @NotNull(message = "O tipo da transação é obrigatório")
        TransactionType type,
        @Size(max = 255, message = "A descrição pode ter no máximo 255 caracteres")
        String description,

        @Pattern(regexp = "\\d{11}", message = "O CPF de origem deve conter exatamente 11 dígitos numéricos")
        String cpfOrigin,

        @Pattern(regexp = "\\d{11}", message = "O CPF de destino deve conter exatamente 11 dígitos numéricos")
        String cpfDestination
) {
}
