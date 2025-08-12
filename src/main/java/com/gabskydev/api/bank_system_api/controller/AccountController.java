package com.gabskydev.api.bank_system_api.controller;

import com.gabskydev.api.bank_system_api.dto.AccountRequestDTO;
import com.gabskydev.api.bank_system_api.dto.AccountResponseDTO;
import com.gabskydev.api.bank_system_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponseDTO> findAccount(@PathVariable UUID userId){
        return new ResponseEntity<>(accountService.findAccountByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/agency")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountResponseDTO>> findAccountsAgency(@RequestParam String agency){
        return new ResponseEntity<>(accountService.findAccountsListByAgency(agency), HttpStatus.OK);
    }

    @GetMapping("/cpf")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AccountResponseDTO> findAccountCPF(@RequestParam String cpf){
        return new ResponseEntity<>(accountService.findAccontByUserCpf(cpf), HttpStatus.OK);
    }

    @GetMapping("/amount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountResponseDTO>> findAccountsAmountGreaterThan(@RequestParam BigDecimal amount){
        return new ResponseEntity<>(accountService.findAccountsListByAmountGreaterThan(amount), HttpStatus.OK);
    }

    @PutMapping("{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable UUID userId, @Valid @RequestBody AccountRequestDTO requestDTO){
        return new ResponseEntity<>(accountService.updateAccountByUserId(userId, requestDTO), HttpStatus.OK);
    }
}
