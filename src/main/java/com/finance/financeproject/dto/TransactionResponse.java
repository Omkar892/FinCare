package com.finance.financeproject.dto;

import com.finance.financeproject.model.Transaction;

public class TransactionResponse {

    private Transaction transaction;
    private boolean budgetExceeded;
    private Double totalSpent;
    private Double budgetLimit;

    public TransactionResponse(Transaction transaction,
                               boolean budgetExceeded,
                               Double totalSpent,
                               Double budgetLimit) {
        this.transaction = transaction;
        this.budgetExceeded = budgetExceeded;
        this.totalSpent = totalSpent;
        this.budgetLimit = budgetLimit;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public boolean isBudgetExceeded() {
        return budgetExceeded;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public Double getBudgetLimit() {
        return budgetLimit;
    }
}
