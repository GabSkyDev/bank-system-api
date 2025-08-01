package com.gabskydev.api.bank_system_api.mapper;

import com.gabskydev.api.bank_system_api.dto.TransactionRequestDTO;
import com.gabskydev.api.bank_system_api.dto.TransactionResponseDTO;
import com.gabskydev.api.bank_system_api.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionResponseDTO toResponse(Transaction transaction){
        return new TransactionResponseDTO(
                transaction.getTimestamp(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getOrigin().getUser().getName(),
                transaction.getOrigin().getUser().getCpf(),
                transaction.getDestination().getUser().getName(),
                transaction.getDestination().getUser().getCpf()
        );
    }

    public Transaction toEntity(TransactionRequestDTO requestDTO){
        return new Transaction(
                requestDTO.id(),
                requestDTO.timestamp(),
                requestDTO.amount(),
                requestDTO.type(),
                requestDTO.description(),
                requestDTO.origin(),
                requestDTO.destination()
        );
    }
}
