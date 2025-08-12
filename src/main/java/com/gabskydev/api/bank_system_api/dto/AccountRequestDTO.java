package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.Transaction;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record AccountRequestDTO(
        UUID id,
        @NotBlank(message = "O número da conta é obrigatório")
        String numberAccount,
        @NotBlank(message = "A agência é obrigatória")
        @Pattern(regexp = "\\d{4}", message = "A agência deve conter exatamente 4 dígitos")
        String agency,
        @NotNull(message = "O saldo é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O saldo não pode ser negativo")
        BigDecimal balance,
        @NotBlank(message = "O CPF do usuário é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos")
        String cpfUser,
        List<Transaction> sentTransactions,
        List<Transaction> receivedTransactions
    ){
    public AccountRequestDTO(UUID id, String numberAccount, String agency, BigDecimal balance, String cpfUser){
        this(id, numberAccount, agency, balance, cpfUser, new ArrayList<>(), new ArrayList<>());
    }
}
