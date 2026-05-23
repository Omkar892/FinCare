package com.finance.financeproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.financeproject.model.Account;
import com.finance.financeproject.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
    return accountService.getAccountById(id);
    }

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
