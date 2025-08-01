package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.UserRole;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record UserRequestDTO(
        UUID id,
        String name,
        String cpf,
        String email,
        String password,
        Account account,
        Set<UserRole> roles
    ){
    public UserRequestDTO(UUID id, String name, String cpf, String email, String password, Account account){
        this(id, name, cpf, email, password, account, new HashSet<>());
    }
}
