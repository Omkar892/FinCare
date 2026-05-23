package com.finance.financeproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.financeproject.model.Transaction;
import com.finance.financeproject.repository.TransactionRepository;
import com.finance.financeproject.dto.TransactionResponse;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetValidationService budgetValidationService;

    public TransactionResponse saveTransaction(Transaction transaction) {

        Transaction saved = transactionRepository.save(transaction);

        ValidationResult result = budgetValidationService.validateBudget(saved.getUser());

        return new TransactionResponse(
                saved,
                result.isExceeded(),
                result.getTotalSpent(),
                result.getLimit()
        );
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
