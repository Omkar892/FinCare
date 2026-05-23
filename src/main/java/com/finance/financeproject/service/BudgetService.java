package com.finance.financeproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finance.financeproject.model.Budget;
import com.finance.financeproject.model.user;
import com.finance.financeproject.repository.BudgetRepository;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    // ✅ Constructor Injection (clean)
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    // ✅ Save Budget
    public Budget saveBudget(Budget budget, user currentUser) {

        // get existing budget for user
        Budget existing = budgetRepository.findByUser(currentUser);

        // check duplicate
        if (existing != null) {

            boolean sameStart = existing.getStartDate().equals(budget.getStartDate());
            boolean sameEnd = existing.getEndDate().equals(budget.getEndDate());

            if (sameStart && sameEnd) {
                throw new RuntimeException("Budget already exists for this period.");
            }
        }

        return budgetRepository.save(budget);
    }

    // ✅ Get all budgets
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
}