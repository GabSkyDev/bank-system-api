package com.gabskydev.api.bank_system_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Contas", description = "Operações relacionadas a contas de usuário")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar conta por ID de usuário", description = "Retorna a conta vinculada ao usuário informado. Apenas administradores podem acessar.")
    public ResponseEntity<AccountResponseDTO> findAccount(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId){
        return new ResponseEntity<>(accountService.findAccountByUserId(userId), HttpStatus.OK);
    }


    @GetMapping("/agency")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar contas por agência", description = "Retorna todas as contas de uma agência específica. Apenas administradores podem acessar.")
    public ResponseEntity<List<AccountResponseDTO>> findAccountsAgency(
            @Parameter(description = "Código da agência", example = "1234")
            @RequestParam String agency){
        return new ResponseEntity<>(accountService.findAccountsListByAgency(agency), HttpStatus.OK);
    }


    @GetMapping("/cpf")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Buscar conta por CPF", description = "Retorna a conta vinculada ao CPF informado. Disponível para administradores e usuários.")
    public ResponseEntity<AccountResponseDTO> findAccountCPF(
            @Parameter(description = "CPF do usuário", example = "12345678900")
            @RequestParam String cpf){
        return new ResponseEntity<>(accountService.findAccontByUserCpf(cpf), HttpStatus.OK);
    }


    @GetMapping("/amount")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar contas com saldo acima de um valor", description = "Retorna todas as contas cujo saldo é maior que o valor informado. Apenas administradores podem acessar.")
    public ResponseEntity<List<AccountResponseDTO>> findAccountsAmountGreaterThan(
            @Parameter(description = "Valor mínimo de saldo", example = "1000.00")
            @RequestParam BigDecimal amount){
        return new ResponseEntity<>(accountService.findAccountsListByAmountGreaterThan(amount), HttpStatus.OK);
    }


    @PutMapping("{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar conta de usuário", description = "Atualiza os dados da conta vinculada ao usuário informado. Apenas administradores podem acessar.")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId,
            @Valid @RequestBody AccountRequestDTO requestDTO){
        return new ResponseEntity<>(accountService.updateAccountByUserId(userId, requestDTO), HttpStatus.OK);
    }
}
