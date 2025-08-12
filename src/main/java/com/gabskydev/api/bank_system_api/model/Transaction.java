package com.gabskydev.api.bank_system_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_tb")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private String description;
    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    private Account origin;
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destination;

    public Transaction(){

    }

    public Transaction(Long id, BigDecimal amount, TransactionType type, String description, Account origin, Account destination) {
        this.id = id;
        this.timestamp = LocalDateTime.now();
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.origin = origin;
        this.destination = destination;
    }
}
