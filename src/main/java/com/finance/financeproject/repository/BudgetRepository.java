package com.finance.financeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.financeproject.model.Budget;
import com.finance.financeproject.model.user;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // get budget of logged-in user
    Budget findByUser(user user);
}