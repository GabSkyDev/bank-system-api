package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.UserRequestDTO;
import com.gabskydev.api.bank_system_api.dto.UserResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.UserMapper;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import com.gabskydev.api.bank_system_api.security.SecurityUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }


    public List<UserResponseDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO getUserByEmail(String email){
        String authEmail = SecurityUtils.getAuthenticatedUserEmail();

        if (!authEmail.equals(email)) {
            throw new RuntimeException("You can only access your own user data.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return userMapper.toResponse(user);
    }

    public UserResponseDTO getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        String authEmail = SecurityUtils.getAuthenticatedUserEmail();

        if(!authEmail.equals(user.getEmail())) {
            throw new RuntimeException("You can only access your own user data.");
        }
        
        return userMapper.toResponse(user);
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO){
        User user = userMapper.toEntity(requestDTO);

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        user.setName(requestDTO.name());
        user.setCpf(requestDTO.cpf());
        user.setEmail(requestDTO.email());
        user.setPassword(requestDTO.password());
        user.setAccount(requestDTO.account());
        user.setRoles((requestDTO.roles()));

        userRepository.save(user);

        return userMapper.toResponse(user);
    }
}
