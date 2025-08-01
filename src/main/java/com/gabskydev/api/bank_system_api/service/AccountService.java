package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.AccountRequestDTO;
import com.gabskydev.api.bank_system_api.dto.AccountResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.AccountMapper;
import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.AccountRepository;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    public AccountResponseDTO findAccountByUserId(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Account account = user.getAccount();

        return accountMapper.toResponse(account);
    }

    public AccountResponseDTO updateAccountByUserId(UUID userId, AccountRequestDTO requestDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Account account = accountMapper.toEntity(requestDTO);
        user.setAccount(account);

        return accountMapper.toResponse(account);
    }
}
