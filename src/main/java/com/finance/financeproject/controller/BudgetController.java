package com.finance.financeproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.finance.financeproject.model.Budget;
import com.finance.financeproject.model.BudgetPeriod;
import com.finance.financeproject.model.TransactionCategory;
import com.finance.financeproject.model.user;
//import com.finance.financeproject.service.BudgetService;
import com.finance.financeproject.repository.UserRepository;
import com.finance.financeproject.repository.BudgetRepository;
import com.finance.financeproject.repository.TransactionCategoryRepository;
@Controller
@RequestMapping("/budget")
public class BudgetController {

   // private final BudgetService budgetService;
    private final UserRepository userRepository;
private final BudgetRepository budgetRepository;
private final TransactionCategoryRepository categoryRepository;
//private final UserRepository userRepository;
     public BudgetController(BudgetRepository budgetRepository,
                        TransactionCategoryRepository categoryRepository,
                        UserRepository userRepository) {

    this.budgetRepository = budgetRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
}

    // show form
    @GetMapping("/set")
    public String showForm(Model model) {
        model.addAttribute("budget", new Budget());
       
        model.addAttribute("categories", categoryRepository.findAll());
        return "set-budget";
    }

    // save budget
@PostMapping("/save")
public String saveBudget(@RequestParam Double amountLimit,
                         @RequestParam Long categoryId,
                         @RequestParam String startDate,
                         @RequestParam String endDate,
                         @RequestParam String periodType) {

    Budget budget = new Budget();

    // set values
    budget.setAmountLimit(amountLimit);
    budget.setStartDate(LocalDate.parse(startDate));
    budget.setEndDate(LocalDate.parse(endDate));
    budget.setPeriodType(BudgetPeriod.valueOf(periodType));

    // 🔥 get category manually
    TransactionCategory category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    budget.setCategory(category);

    // 🔥 get user
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();

    user currentUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    budget.setUser(currentUser);

    // save
    budgetRepository.save(budget);

    return "redirect:/dashboard";
}
}