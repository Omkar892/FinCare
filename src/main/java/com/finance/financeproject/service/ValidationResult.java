package com.finance.financeproject.service;

public class ValidationResult {

    private boolean exceeded;
    private Double totalSpent;
    private Double limit;

    public ValidationResult(boolean exceeded, Double totalSpent, Double limit) {
        this.exceeded = exceeded;
        this.totalSpent = totalSpent;
        this.limit = limit;
    }

    public boolean isExceeded() {
        return exceeded;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public Double getLimit() {
        return limit;
    }
}
