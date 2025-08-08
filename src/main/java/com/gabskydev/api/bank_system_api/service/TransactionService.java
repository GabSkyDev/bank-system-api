package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.TransactionMapper;
import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.Transaction;
import com.gabskydev.api.bank_system_api.repository.AccountRepository;
import com.gabskydev.api.bank_system_api.repository.TransactionRepository;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, UserRepository userRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionResponseDTO> AllTransactionListByAccountId(UUID accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));


        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getReceivedTransactions());
        transactions.addAll(account.getSentTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }
    public List<TransactionResponseDTO> getSentTransactionListByAccountId(UUID accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getSentTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }
    public List<TransactionResponseDTO> getReceivedTransactionListByAccountId(UUID accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getReceivedTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }
    public TransactionResponseDTO getTransactionById(UUID accountId, Long transactionId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getReceivedTransactions());
        transactions.addAll(account.getSentTransactions());

        Transaction transaction = transactions
                .stream()
                .filter(t -> t.getId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Transaction not found!"));


        return transactionMapper.toResponse(transaction);
    }
    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO){
        Account origin = accountRepository.findByUserCpf(requestDTO.cpfOrigin())
                .orElseThrow(() -> new RuntimeException("Origin account not found!"));

        Account destination = accountRepository.findByUserCpf(requestDTO.cpfDestination())
                .orElseThrow(() -> new RuntimeException("Destination account not found!"));

        origin.setBalance(origin.getBalance().subtract(requestDTO.amount()));
        destination.setBalance(destination.getBalance().add(requestDTO.amount()));

        Transaction transaction = transactionMapper.toEntity(requestDTO, origin, destination);
        origin.getSentTransactions().add(transaction);
        destination.getReceivedTransactions().add(transaction);

        transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }
    public void deleteTransactionById(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }
}
