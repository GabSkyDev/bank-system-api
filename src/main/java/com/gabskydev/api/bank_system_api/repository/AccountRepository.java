package com.gabskydev.api.bank_system_api.repository;

import com.gabskydev.api.bank_system_api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
