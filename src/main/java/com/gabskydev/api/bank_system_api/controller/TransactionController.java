package com.gabskydev.api.bank_system_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{agency}/{numberAccount}/transactions")
@Tag(name = "Transações", description = "Operações relacionadas a transações bancárias")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        @Operation(summary = "Listar todas as transações", description = "Retorna todas as transações da conta informada. Disponível para administradores e usuários.")
        public ResponseEntity<List<TransactionResponseDTO>> findAllTransactions(
                        @Parameter(description = "Código da agência", example = "1234")
                        @PathVariable String agency,
                        @Parameter(description = "Número da conta", example = "56789-0")
                        @PathVariable String numberAccount){
                return new ResponseEntity<>(
                                transactionService.AllTransactionList(agency, numberAccount),
                                HttpStatus.OK);
        }


        @GetMapping("/received")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        @Operation(summary = "Listar transações recebidas", description = "Retorna todas as transações recebidas pela conta informada. Disponível para administradores e usuários.")
        public ResponseEntity<List<TransactionResponseDTO>> findReceivedTransactionList(
                        @Parameter(description = "Código da agência", example = "1234")
                        @PathVariable String agency,
                        @Parameter(description = "Número da conta", example = "56789-0")
                        @PathVariable String numberAccount){
                return new ResponseEntity<>(
                                transactionService.getSentTransactionList(agency, numberAccount),
                                HttpStatus.OK);
        }


        @GetMapping("/sent")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        @Operation(summary = "Listar transações enviadas", description = "Retorna todas as transações enviadas pela conta informada. Disponível para administradores e usuários.")
        public ResponseEntity<List<TransactionResponseDTO>> findSentTransactionList(
                        @Parameter(description = "Código da agência", example = "1234")
                        @PathVariable String agency,
                        @Parameter(description = "Número da conta", example = "56789-0")
                        @PathVariable String numberAccount){
                return new ResponseEntity<>(
                                transactionService.getReceivedTransactionList(agency, numberAccount),
                                HttpStatus.OK);
        }


        @GetMapping("/{transactionId}")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        @Operation(summary = "Buscar transação por ID", description = "Retorna os detalhes de uma transação específica da conta informada. Disponível para administradores e usuários.")
        public ResponseEntity<TransactionResponseDTO> getTransaction(
                        @Parameter(description = "Código da agência", example = "1234")
                        @PathVariable String agency,
                        @Parameter(description = "Número da conta", example = "56789-0")
                        @PathVariable String numberAccount,
                        @Parameter(description = "ID da transação", example = "1")
                        @PathVariable Long transactionId){
                return new ResponseEntity<>(
                                transactionService.getTransaction(agency, numberAccount, transactionId),
                                HttpStatus.OK);
        }


        @PostMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        @Operation(summary = "Criar nova transação", description = "Cria uma nova transação para a conta informada. Disponível para administradores e usuários.")
        public ResponseEntity<TransactionResponseDTO> createTransaction(
                        @Parameter(description = "Código da agência", example = "1234")
                        @PathVariable String agency,
                        @Parameter(description = "Número da conta", example = "56789-0")
                        @PathVariable String numberAccount,
                        @Valid @RequestBody TransactionRequestDTO requestDTO){
                return new ResponseEntity<>(
                                transactionService.createTransaction(agency, numberAccount, requestDTO),
                                HttpStatus.CREATED);
        }


        @DeleteMapping("/{transactionId}")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Deletar transação", description = "Remove uma transação pelo ID. Apenas administradores podem acessar.")
        public ResponseEntity<Void> deleteTransaction(
                        @Parameter(description = "ID da transação", example = "1")
                        @PathVariable Long transactionId){
                transactionService.deleteTransactionById(transactionId);
                return new ResponseEntity<>(HttpStatus.OK);
        }


        @PutMapping("/{transactionId}")
        @PreAuthorize("hasRole('ADMIN', 'USER')")
        @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente. Disponível para administradores e usuários.")
        public ResponseEntity<TransactionResponseDTO> updateTransaction(
                        @Parameter(description = "Código da agência", example = "1234")
                        @PathVariable String agency,
                        @Parameter(description = "Número da conta", example = "56789-0")
                        @PathVariable String numberAccount,
                        @Parameter(description = "ID da transação", example = "1")
                        @PathVariable Long transactionId,
                        @RequestBody TransactionRequestDTO requestDTO){
                return new ResponseEntity<>(
                                transactionService.updateTransactionById(agency, numberAccount, transactionId, requestDTO),
                                HttpStatus.OK);
        }
}
