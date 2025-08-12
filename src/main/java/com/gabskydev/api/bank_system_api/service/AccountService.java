package com.gabskydev.api.bank_system_api.service;

import com.gabskydev.api.bank_system_api.dto.AccountRequestDTO;
import com.gabskydev.api.bank_system_api.dto.AccountResponseDTO;
import com.gabskydev.api.bank_system_api.mapper.AccountMapper;
import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.User;
import com.gabskydev.api.bank_system_api.repository.AccountRepository;
import com.gabskydev.api.bank_system_api.repository.UserRepository;
import com.gabskydev.api.bank_system_api.security.SecurityService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final SecurityService securityService;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository,
                          AccountMapper accountMapper, SecurityService securityService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
        this.securityService = securityService;
    }

    public AccountResponseDTO findAccountByUserId(UUID userId) {
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getId().equals(userId)){
            throw new RuntimeException("Access denied");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Account account = user.getAccount();

        return accountMapper.toResponse(account);
    }

    public AccountResponseDTO updateAccountByUserId(UUID userId, AccountRequestDTO requestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Account account = accountMapper.toEntity(requestDTO, user);
        user.setAccount(account);

        userRepository.save(user);

        return accountMapper.toResponse(account);
    }

    public List<AccountResponseDTO> findAccountsListByAgency(String agency) {
        List<Account> accountList = accountRepository.findAllByAgency(agency)
                .orElseThrow(() -> new RuntimeException("Agency not found!"));

        return accountList
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    public List<AccountResponseDTO> findAccountsListByAmountGreaterThan(BigDecimal amount) {
        List<Account> accountList = accountRepository.findAllByBalanceGreaterThan(amount)
                .orElseThrow(() -> new RuntimeException("Account with amount greater not found!"));

        return accountList
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    public AccountResponseDTO findAccontByUserCpf(String cpf){
        User loggedUser = securityService.getLoggedUser();

        if (!loggedUser.getCpf().equals(cpf)){
            throw new RuntimeException("Access denied");
        }

        Account account = accountRepository.findByUserCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        return accountMapper.toResponse(account);
    }
}
