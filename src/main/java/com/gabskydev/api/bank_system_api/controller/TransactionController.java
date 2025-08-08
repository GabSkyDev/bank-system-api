package com.gabskydev.api.bank_system_api.controller;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAllTransactions(@PathVariable UUID accountId){
        return new ResponseEntity<>(transactionService.AllTransactionListByAccountId(accountId), HttpStatus.OK);
    }

    @GetMapping("/received")
    public ResponseEntity<List<TransactionResponseDTO>> findReceivedTransactionList(@PathVariable UUID accountId){
        return new ResponseEntity<>(transactionService.getSentTransactionListByAccountId(accountId), HttpStatus.OK);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<TransactionResponseDTO>> findSentTransactionList(@PathVariable UUID accountId){
        return new ResponseEntity<>(transactionService.getReceivedTransactionListByAccountId(accountId), HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransaction(@PathVariable UUID accountId, @PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.getTransactionById(accountId, transactionId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO requestDTO){
        return new ResponseEntity<>(transactionService.createTransaction(requestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
