package com.finance.financeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.financeproject.model.TransactionCategory;

public interface CategoryRepository extends JpaRepository<TransactionCategory, Long> {
}