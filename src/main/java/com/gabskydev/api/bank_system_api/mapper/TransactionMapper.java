package com.gabskydev.api.bank_system_api.mapper;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.model.Account;
import com.gabskydev.api.bank_system_api.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionResponseDTO toResponse(Transaction transaction){
        String originName = null;
        String originCpf = null;
        String destinationName = null;
        String destinationCpf = null;

        if (transaction.getOrigin() != null) {
            originName = transaction.getOrigin().getUser().getName();
            originCpf = transaction.getOrigin().getUser().getCpf();
        }

        if (transaction.getDestination() != null) {
            destinationName = transaction.getDestination().getUser().getName();
            destinationCpf = transaction.getDestination().getUser().getCpf();
        }

        return new TransactionResponseDTO(
                transaction.getTimestamp(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDescription(),
                originName,
                originCpf,
                destinationName,
                destinationCpf
        );
    }

    public Transaction toEntity(TransactionRequestDTO requestDTO, Account origin, Account destination){
        return new Transaction(
                requestDTO.id(),
                requestDTO.amount(),
                requestDTO.type(),
                requestDTO.description(),
                origin,
                destination
        );
    }
}
