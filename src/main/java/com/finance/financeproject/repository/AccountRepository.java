package com.finance.financeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.financeproject.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}