package com.gabskydev.api.bank_system_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_tb")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }
}
