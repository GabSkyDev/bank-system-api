package com.gabskydev.api.bank_system_api.dto;

import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.UserRole;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record UserRequestDTO(
        UUID id,
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @Pattern(
                regexp = "\\d{11}",
                message = "O CPF deve conter exatamente 11 dígitos numéricos"
        )
        String cpf,
        @Email(message = "O e-mail deve ser válido")
        String email,
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String password,
        @NotNull(message = "A conta é obrigatória")
        Account account,
        @NotEmpty(message = "O usuário deve ter pelo menos um papel")
        Set<UserRole> roles
    ){
    public UserRequestDTO(UUID id, String name, String cpf, String email, String password, Account account){
        this(id, name, cpf, email, password, account, new HashSet<>());
    }
}
