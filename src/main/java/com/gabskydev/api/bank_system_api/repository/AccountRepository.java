package com.gabskydev.api.bank_system_api.repository;

import com.gabskydev.api.bank_system_api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserCpf(String cpf);
    Optional<List<Account>> findAllByAgency(String agency);
    Optional<List<Account>> findAllByBalanceGreaterThan(BigDecimal amount);
    Optional<Account> findByAgencyAndNumberAccount(String agency, String numberAccount);
}
