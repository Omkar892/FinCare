package com.finance.financeproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.finance.financeproject.model.Transaction;
import com.finance.financeproject.model.user;
import com.finance.financeproject.model.Account;

import com.finance.financeproject.repository.TransactionRepository;
import com.finance.financeproject.repository.CategoryRepository;
import com.finance.financeproject.repository.UserRepository;
import com.finance.financeproject.repository.AccountRepository;

@Controller
@RequestMapping("/transactions")
public class TransactionPageController {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository; // ✅ NEW

    // ✅ Updated constructor
    public TransactionPageController(TransactionRepository transactionRepository,
                                     CategoryRepository categoryRepository,
                                     UserRepository userRepository,
                                     AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    // ✅ SHOW FORM
    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("accounts", accountRepository.findAll()); // ✅ IMPORTANT
        return "add-transaction";
    }

    // ✅ SAVE TRANSACTION (FIXED)
@PostMapping("/save")
public String saveTransaction(@ModelAttribute Transaction transaction) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    // ✅ FIX HERE
    user currentUser = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    transaction.setUser(currentUser);

    if (transaction.getAccount() == null || transaction.getAccount().getId() == null) {
        throw new RuntimeException("Please select an account");
    }

    Account account = accountRepository.findById(transaction.getAccount().getId())
            .orElseThrow(() -> new RuntimeException("Account not found"));

    transaction.setAccount(account);

    transactionRepository.save(transaction);

    return "redirect:/dashboard";
}

    // ✅ DELETE
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);
        return "redirect:/dashboard";
    }
}