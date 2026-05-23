package com.finance.financeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.financeproject.model.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}