package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.UserRequestDTO;
import com.gabskydev.api.bank_system_api.dto.UserResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.UserMapper;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> getAllUser(){
        List<User> userList = userRepository.findAll();
        return userList
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return userMapper.toResponse(user);
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO){
        User user = userMapper.toEntity(requestDTO);

        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    public void deleteUserById(UUID id){
        userRepository.deleteById(id);
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
