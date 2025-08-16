package com.gabskydev.api.bank_system_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.gabskydev.api.bank_system_api.dto.UserRequestDTO;
import com.gabskydev.api.bank_system_api.dto.UserResponseDTO;
import com.gabskydev.api.bank_system_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários. Apenas administradores podem acessar.")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Buscar usuário por e-mail", description = "Retorna os dados do usuário pelo e-mail. Disponível para administradores e usuários.")
    public ResponseEntity<UserResponseDTO> getByEmail(
            @Parameter(description = "E-mail do usuário", example = "usuario@email.com")
            @PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }


    @GetMapping("/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados do usuário pelo ID. Apenas administradores podem acessar.")
    public ResponseEntity<UserResponseDTO> getUser(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário. Apenas administradores podem acessar.")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO requestDTO){
        return new ResponseEntity<>(userService.createUser(requestDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente. Apenas administradores podem acessar.")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId,
            @Valid @RequestBody UserRequestDTO requestDTO){
        return new ResponseEntity<>(userService.updateUser(userId, requestDTO), HttpStatus.OK);
    }
}
