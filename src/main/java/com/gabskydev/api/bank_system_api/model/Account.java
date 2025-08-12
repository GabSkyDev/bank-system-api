package com.gabskydev.api.bank_system_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account_tb")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String numberAccount;
    private String agency;
    private BigDecimal balance;
    @OneToOne(mappedBy = "account")
    private User user;
    @OneToMany(mappedBy = "origin")
    private List<Transaction> sentTransactions;
    @OneToMany(mappedBy = "destination")
    private List<Transaction> receivedTransactions;

    public Account(){

    }

    public Account(UUID id, String numberAccount, String agency, BigDecimal balance, User user, List<Transaction> sentTransactions, List<Transaction> receivedTransactions) {
        this.id = id;
        this.numberAccount = numberAccount;
        this.agency = agency;
        this.balance = balance;
        this.user = user;
        this.sentTransactions = sentTransactions;
        this.receivedTransactions = receivedTransactions;
    }

    public void debit(BigDecimal amount){
        if (balance.compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient funds");
        }

        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount){
        balance = balance.add(amount);
    }
}
