package com.finance.financeproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.financeproject.model.Account;
import com.finance.financeproject.repository.AccountRepository;

@Service
public class AccountService {
    public Account getAccountById(Long id) {
    return accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Account not found"));
}

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}

