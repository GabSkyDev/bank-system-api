package com.gabskydev.api.bank_system_api.security;

import com.gabskydev.api.bank_system_api.dto.LoginRequestDTO;
import com.gabskydev.api.bank_system_api.dto.LoginResponseDTO;
import com.gabskydev.api.bank_system_api.dto.UserRequestDTO;
import com.gabskydev.api.bank_system_api.dto.UserResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.UserMapper;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            TokenService tokenService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request) {
        try {
            var emailPassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            var auth = authenticationManager.authenticate(emailPassword);

            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            var token = tokenService.generateToken(userDetails.getUser());

            return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO request) {
        if (this.userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.id(), request.name(), request.cpf(), request.email(), encryptedPassword,
                request.account(), request.roles());

        this.userRepository.save(newUser);

        return new ResponseEntity<>(userMapper.toResponse(newUser), HttpStatus.CREATED);
    }
}
