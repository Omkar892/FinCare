package com.finance.financeproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.financeproject.model.Transaction;
import com.finance.financeproject.model.Account;
import com.finance.financeproject.dto.TransactionResponse;
import com.finance.financeproject.service.TransactionService;
import com.finance.financeproject.repository.AccountRepository;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    // ✅ CREATE TRANSACTION (FIXED)
    @PostMapping
    public TransactionResponse createTransaction(@RequestBody Transaction transaction) {

        
        // 🔥 IMPORTANT FIX: Check account
        if (transaction.getAccount() == null || transaction.getAccount().getId() == null) {
            throw new RuntimeException("Account is required!");
        }

        // Fetch full account from DB
        Account account = accountRepository.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        transaction.setAccount(account);

        return transactionService.saveTransaction(transaction);
    }

    // ✅ GET ALL TRANSACTIONS
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}