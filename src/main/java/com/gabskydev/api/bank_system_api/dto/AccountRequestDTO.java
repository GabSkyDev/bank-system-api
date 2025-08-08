package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record AccountRequestDTO(
        UUID id,
        String numberAccount,
        String agency,
        BigDecimal balance,
        String cpfUser,
        List<Transaction> sentTransactions,
        List<Transaction> receivedTransactions
    ){
    public AccountRequestDTO(UUID id, String numberAccount, String agency, BigDecimal balance, String cpfUser){
        this(id, numberAccount, agency, balance, cpfUser, new ArrayList<>(), new ArrayList<>());
    }
}
