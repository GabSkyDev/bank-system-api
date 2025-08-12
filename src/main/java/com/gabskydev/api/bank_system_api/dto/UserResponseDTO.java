package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.UserRole;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String cpf,
        String email,
        Set<UserRole> roles
    ){}
