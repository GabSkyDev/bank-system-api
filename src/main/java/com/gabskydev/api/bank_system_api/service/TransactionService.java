package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.TransactionMapper;
import com.gabskydev.api.bank_system_api.model.Transaction;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.TransactionRepository;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionResponseDTO> AllTransactionListByUserId(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(user.getAccount().getReceivedTransactions());
        transactions.addAll(user.getAccount().getSentTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }

    public List<TransactionResponseDTO> getSentTransactionListByUserId(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(user.getAccount().getSentTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }

    public List<TransactionResponseDTO> getReceivedTransactionListByUserId(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(user.getAccount().getReceivedTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }

    public TransactionResponseDTO getTransactionById(UUID userId, Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found!"));

        return transactionMapper.toResponse(transaction);
    }
    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO){
        Transaction transaction = transactionMapper.toEntity(requestDTO);
        transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }

    public void deleteTransactionById(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }
}
