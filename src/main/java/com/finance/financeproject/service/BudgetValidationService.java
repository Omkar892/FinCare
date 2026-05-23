package com.finance.financeproject.service;

import org.springframework.stereotype.Service;

import com.finance.financeproject.model.Budget;
import com.finance.financeproject.model.Transaction;
import com.finance.financeproject.model.user;
import com.finance.financeproject.repository.BudgetRepository;
import com.finance.financeproject.repository.TransactionRepository;
import com.finance.financeproject.repository.UserRepository;

import java.util.List;

@Service
public class BudgetValidationService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // ✅ Constructor Injection
    public BudgetValidationService(BudgetRepository budgetRepository,
                                   TransactionRepository transactionRepository,
                                   UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // 🔥 MAIN VALIDATION METHOD
    public ValidationResult validateBudget(user currentUser) {

        // 🔹 Safety check (important)
        if (currentUser == null) {
            throw new RuntimeException("User is null");
        }

        // 🔹 Get user's budget
        Budget budget = budgetRepository.findByUser(currentUser);

        if (budget == null) {
            return new ValidationResult(false, 0.0, 0.0);
        }

        // 🔹 Get user transactions
        List<Transaction> transactions = transactionRepository.findByUser(currentUser);

        double totalExpense = 0.0;

        for (Transaction t : transactions) {
            if ("EXPENSE".equalsIgnoreCase(t.getType())) {
                totalExpense += (t.getAmount() != null ? t.getAmount() : 0.0);
            }
        }

        double limit = budget.getAmountLimit() != null ? budget.getAmountLimit() : 0.0;

        boolean exceeded = totalExpense > limit;

        return new ValidationResult(exceeded, totalExpense, limit);
    }
}