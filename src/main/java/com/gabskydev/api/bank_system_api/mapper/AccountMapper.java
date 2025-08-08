package com.gabskydev.api.bank_system_api.mapper;

import com.gabskydev.api.bank_system_api.dto.AccountRequestDTO;
import com.gabskydev.api.bank_system_api.dto.AccountResponseDTO;
import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponseDTO toResponse(Account account){
        return new AccountResponseDTO(
                account.getNumberAccount(),
                account.getAgency(),
                account.getBalance(),
                account.getUser().getCpf(),
                account.getUser().getName()
        );
    }

    public Account toEntity(AccountRequestDTO requestDTO, User user){
        return new Account(
                requestDTO.id(),
                requestDTO.numberAccount(),
                requestDTO.agency(),
                requestDTO.balance(),
                user,
                requestDTO.sentTransactions(),
                requestDTO.receivedTransactions()
        );
    }
}
