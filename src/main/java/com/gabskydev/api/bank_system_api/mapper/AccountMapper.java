package com.gabskydev.api.bank_system_api.mapper;

import com.gabskydev.api.bank_system_api.dto.AccountRequestDTO;
import com.gabskydev.api.bank_system_api.dto.AccountResponseDTO;
import com.gabskydev.api.bank_system_api.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponseDTO toResponse(Account account){
        return new AccountResponseDTO(
                account.getNumberAccount(),
                account.getAgency(),
                account.getBalance(),
                account.getUser(),
                account.getSentTransactions(),
                account.getReceivedTransactions()
        );
    }

    public Account toEntity(AccountRequestDTO requestDTO){
        return new Account(
                requestDTO.id(),
                requestDTO.numberAccount(),
                requestDTO.agency(),
                requestDTO.balance(),
                requestDTO.user(),
                requestDTO.sentTransactions(),
                requestDTO.receivedTransactions()
        );
    }
}
