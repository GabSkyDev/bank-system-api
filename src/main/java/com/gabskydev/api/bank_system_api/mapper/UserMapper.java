package com.gabskydev.api.bank_system_api.mapper;

import com.gabskydev.api.bank_system_api.dto.UserRequestDTO;
import com.gabskydev.api.bank_system_api.dto.UserResponseDTO;
import com.gabskydev.api.bank_system_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toResponse(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
        );
    }

    public User toEntity(UserRequestDTO requestDTO){
        return new User(
                requestDTO.id(),
                requestDTO.name(),
                requestDTO.cpf(),
                requestDTO.email(),
                requestDTO.password(),
                requestDTO.account(),
                requestDTO.roles()
        );
    }
}
