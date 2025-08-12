package com.gabskydev.api.bank_system_api.dto;

import java.math.BigDecimal;


public record AccountResponseDTO(
        String numberAccount,
        String agency,
        BigDecimal balance,
        String cpfUser,
        String nameUser
    ){}
