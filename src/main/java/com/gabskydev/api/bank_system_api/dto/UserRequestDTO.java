package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.UserRole;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record UserRequestDTO(
        UUID id,
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Pattern(
                regexp = "\\d{11}",
                message = "The CPF origin need to have exactly 11 digits"
        )
        String cpf,
        @Email(message = "Email need to be valid")
        String email,
        @Size(min = 6, max = 20, message = "The description maximum size is 255 characters")
        String password,
        @NotNull(message = "Account cannot be blank")
        Account account,
        @NotEmpty(message = "User needs to have at least one role")
        Set<UserRole> roles
    ){
    public UserRequestDTO(UUID id, String name, String cpf, String email, String password, Account account){
        this(id, name, cpf, email, password, account, new HashSet<>());
    }
}
