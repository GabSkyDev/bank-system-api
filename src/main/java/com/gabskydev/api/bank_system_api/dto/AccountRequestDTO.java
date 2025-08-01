package com.gabskydev.api.bank_system_api.dto;


import com.gabskydev.api.bank_system_api.model.Transaction;
import com.gabskydev.api.bank_system_api.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record AccountRequestDTO(
        UUID id,
        String numberAccount,
        String agency,
        BigDecimal balance,
        User user,
        List<Transaction> sentTransactions,
        List<Transaction> receivedTransactions
    ){
    public AccountRequestDTO(UUID id, String numberAccount, String agency, BigDecimal balance, User user){
        this(id, numberAccount, agency, balance, user, new ArrayList<>(), new ArrayList<>());
    }
}
