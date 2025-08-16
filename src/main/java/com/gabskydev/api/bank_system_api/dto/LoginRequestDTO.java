package com.gabskydev.api.bank_system_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email need to be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password need to have between 6 and 20 characters")
        String password
) {
}
