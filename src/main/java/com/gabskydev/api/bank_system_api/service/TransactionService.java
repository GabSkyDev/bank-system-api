package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.TransactionMapper;
import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.Transaction;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.AccountRepository;
import com.gabskydev.api.bank_system_api.repository.TransactionRepository;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import com.gabskydev.api.bank_system_api.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;
    private final SecurityService securityService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, UserRepository userRepository, TransactionMapper transactionMapper, SecurityService securityService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
        this.securityService = securityService;
    }

    public List<TransactionResponseDTO> AllTransactionList(String agency, String numberAccount){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getAccount().getAgency().equals(agency)
                || !loggedUser.getAccount().getNumberAccount().equals(numberAccount)){
            throw new RuntimeException("Access denied");
        }

        Account account = accountRepository.findByAgencyAndNumberAccount(agency, numberAccount)
                .orElseThrow(() -> new RuntimeException("Account not found!"));


        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getReceivedTransactions());
        transactions.addAll(account.getSentTransactions());

        return transactions
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
    public List<TransactionResponseDTO> getSentTransactionList(String agency, String numberAccount){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getAccount().getAgency().equals(agency)
                || !loggedUser.getAccount().getNumberAccount().equals(numberAccount)){
            throw new RuntimeException("Access denied");
        }


        Account account = accountRepository.findByAgencyAndNumberAccount(agency, numberAccount)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getSentTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }
    public List<TransactionResponseDTO> getReceivedTransactionList(String agency, String numberAccount){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getAccount().getAgency().equals(agency)
                || !loggedUser.getAccount().getNumberAccount().equals(numberAccount)){
            throw new RuntimeException("Access denied");
        }


        Account account = accountRepository.findByAgencyAndNumberAccount(agency, numberAccount)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        List<Transaction> transactions = new ArrayList<>();

        transactions.addAll(account.getReceivedTransactions());

        return transactions.stream().map(transactionMapper::toResponse).toList();
    }
    public TransactionResponseDTO getTransaction(String agency, String numberAccount, Long transactionId){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getAccount().getAgency().equals(agency)
                || !loggedUser.getAccount().getNumberAccount().equals(numberAccount)){
            throw new RuntimeException("Access denied");
        }


        Account account = accountRepository.findByAgencyAndNumberAccount(agency, numberAccount)
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
    public TransactionResponseDTO createTransaction(String agency, String numberAccount, TransactionRequestDTO requestDTO){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getCpf().equals(requestDTO.cpfOrigin())){
            throw new RuntimeException("Access denied");
        }

        if (!loggedUser.getAccount().getAgency().equals(agency)
                || !loggedUser.getAccount().getNumberAccount().equals(numberAccount)){
            throw new RuntimeException("Access denied");
        }

        String type = requestDTO.type().toString();

        return switch (type){
            case "TRANSFER" -> processTransfer(requestDTO);
            case "WITHDRAWAL" -> processWithdrawal(requestDTO);
            case "DEPOSIT" -> processDeposit(requestDTO);
            default -> throw new RuntimeException("Invalid transaction type");
        };
    }
    public void deleteTransactionById(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }
    public TransactionResponseDTO updateTransactionById(String agency, String numberAccount, Long transactionId, TransactionRequestDTO requestDTO){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getAccount().getAgency().equals(agency)
                || !loggedUser.getAccount().getNumberAccount().equals(numberAccount)){
            throw new RuntimeException("Access denied");
        }


        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found!"));

        Account origin = accountRepository.findByUserCpf(requestDTO.cpfOrigin())
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        Account destination = accountRepository.findByUserCpf(requestDTO.cpfDestination())
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        transaction.setAmount(requestDTO.amount());
        transaction.setType(requestDTO.type());
        transaction.setDescription(requestDTO.description());
        transaction.setOrigin(origin);
        transaction.setDestination(destination);

        transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

    private TransactionResponseDTO processTransfer(TransactionRequestDTO requestDTO){
        Account origin = accountRepository.findByUserCpf(requestDTO.cpfOrigin())
                .orElseThrow(() -> new RuntimeException("Origin account not found!"));

        Account destination = accountRepository.findByUserCpf(requestDTO.cpfDestination())
                .orElseThrow(() -> new RuntimeException("Destination account not found!"));

        origin.credit(requestDTO.amount());
        destination.credit(requestDTO.amount());

        Transaction transaction = transactionMapper.toEntity(requestDTO, origin, destination);
        transaction.setTimestamp(LocalDateTime.now());
        origin.getSentTransactions().add(transaction);
        destination.getReceivedTransactions().add(transaction);

        transactionRepository.save(transaction);

        return transactionMapper.toResponse(transaction);
    }

    private TransactionResponseDTO processWithdrawal(TransactionRequestDTO requestDTO){
        Account origin = accountRepository.findByUserCpf(requestDTO.cpfOrigin())
                .orElseThrow(() -> new RuntimeException("Origin account not found!"));

        if (origin.getBalance().compareTo(requestDTO.amount()) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        origin.debit(requestDTO.amount());

        Transaction transaction = transactionMapper.toEntity(requestDTO, origin, null);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        origin.getSentTransactions().add(transaction);

        return transactionMapper.toResponse(transaction);
    }

    private TransactionResponseDTO processDeposit(TransactionRequestDTO requestDTO){
        Account origin = accountRepository.findByUserCpf(requestDTO.cpfOrigin())
                .orElseThrow(() -> new RuntimeException("Origin account not found!"));

        origin.credit(requestDTO.amount());

        Transaction transaction = transactionMapper.toEntity(requestDTO, null, origin);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        origin.getReceivedTransactions().add(transaction);

        return  transactionMapper.toResponse(transaction);
    }
}
