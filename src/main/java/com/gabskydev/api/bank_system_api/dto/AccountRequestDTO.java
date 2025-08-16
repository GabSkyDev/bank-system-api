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
        @NotBlank(message = "Number account cannot be blank")
        String numberAccount,
        @NotBlank(message = "Agency cannot be blank")
        @Pattern(regexp = "\\d{4}", message = "The agency need to have exactly numeric 4 digits")
        String agency,
        @NotNull(message = "Balance cannot be blank")
        @DecimalMin(value = "0.0", inclusive = true, message = "The balance cannot be negative")
        BigDecimal balance,
        @NotBlank(message = "CPF cannot be blank")
        @Pattern(regexp = "\\d{11}", message = "The CPF need to contain exactly 11 numeric digits")
        String cpfUser,
        List<Transaction> sentTransactions,
        List<Transaction> receivedTransactions
    ){
    public AccountRequestDTO(UUID id, String numberAccount, String agency, BigDecimal balance, String cpfUser){
        this(id, numberAccount, agency, balance, cpfUser, new ArrayList<>(), new ArrayList<>());
    }
}
