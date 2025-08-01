package com.gabskydev.api.bank_system_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account_tb")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }
}
