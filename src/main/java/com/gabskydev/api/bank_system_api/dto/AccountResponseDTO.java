package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.Transaction;
import com.gabskydev.api.bank_system_api.model.User;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountResponseDTO(
        String numberAccount,
        String agency,
        BigDecimal balance,
        User user,
        List<Transaction> sentTransactions,
        List<Transaction> receivedTransactions
    ){
}
