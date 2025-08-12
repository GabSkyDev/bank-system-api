package com.gabskydev.api.bank_system_api.controller;

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
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/id/{userId}")
    @PreAuthorize("hasAnyHole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO){
        return new ResponseEntity<>(userService.createUser(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserRequestDTO requestDTO){
        return new ResponseEntity<>(userService.updateUser(userId, requestDTO), HttpStatus.OK);
    }
}
