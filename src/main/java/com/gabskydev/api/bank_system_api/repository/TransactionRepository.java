package com.gabskydev.api.bank_system_api.repository;

import com.gabskydev.api.bank_system_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
