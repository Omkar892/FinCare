package com.finance.financeproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.finance.financeproject.repository.GoalRepository;
import com.finance.financeproject.repository.UserRepository;
import com.finance.financeproject.repository.TransactionRepository;

import com.finance.financeproject.model.Goal;
import com.finance.financeproject.model.Transaction;
import com.finance.financeproject.model.user;

@Controller
public class DashboardController {

    private final TransactionRepository transactionRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public DashboardController(TransactionRepository transactionRepository,
                               GoalRepository goalRepository,
                               UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        // 🔹 Logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Role check
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // ================= ADMIN =================
        if (isAdmin) {
            model.addAttribute("users", userRepository.findAll());
            return "admin-dashboard";
        }

        // ================= USER =================

        // 🔹 Income & Expense
        Double income = transactionRepository.getTotalIncomeByUser(currentUser);
        Double expense = transactionRepository.getTotalExpenseByUser(currentUser);

        if (income == null) income = 0.0;
        if (expense == null) expense = 0.0;

        Double remaining = income - expense;

        // 🔹 Category chart
        List<Object[]> data = transactionRepository.getCategoryWiseExpensesByUser(currentUser);
        List<String> categories = new ArrayList<>();
        List<Double> amounts = new ArrayList<>();

        for (Object[] row : data) {
            categories.add((String) row[0]);
            amounts.add((Double) row[1]);
        }

        // 🔹 Monthly chart
        List<Object[]> monthlyData = transactionRepository.getMonthlyExpensesByUser(currentUser);
        List<String> months = new ArrayList<>();
        List<Double> monthlyAmounts = new ArrayList<>();

        for (Object[] row : monthlyData) {
            Integer month = (Integer) row[0];
            Double amount = (Double) row[1];

            String name = switch (month) {
                case 1 -> "Jan"; case 2 -> "Feb"; case 3 -> "Mar";
                case 4 -> "Apr"; case 5 -> "May"; case 6 -> "Jun";
                case 7 -> "Jul"; case 8 -> "Aug"; case 9 -> "Sep";
                case 10 -> "Oct"; case 11 -> "Nov"; case 12 -> "Dec";
                default -> "";
            };

            months.add(name);
            monthlyAmounts.add(amount);
        }

        // 🔹 Transactions
        List<Transaction> transactions = transactionRepository.findByUser(currentUser);

        // 🔹 Goals
        List<Goal> goals = goalRepository.findAll();

        // 🔥 🔥 BUDGET ALERT (FIXED POSITION)
        List<Object[]> budgetAlerts = transactionRepository.getBudgetUsage(currentUser);

        List<String> budgetCategories = new ArrayList<>();
        List<Double> spentList = new ArrayList<>();
        List<Double> limitList = new ArrayList<>();
        List<Double> percentList = new ArrayList<>();

        for (Object[] row : budgetAlerts) {
            String category = (String) row[0];
            Double spent = (Double) row[1];
            Double limit = (Double) row[2];

            if (spent == null) spent = 0.0;
            if (limit == null) limit = 0.0;

            double percent = limit > 0 ? (spent * 100 / limit) : 0;

            budgetCategories.add(category);
            spentList.add(spent);
            limitList.add(limit);
            percentList.add(percent);
        }

        // ================= MODEL =================
        model.addAttribute("income", income);
        model.addAttribute("expense", expense);
        model.addAttribute("remaining", remaining);

        model.addAttribute("categories", categories);
        model.addAttribute("amounts", amounts);

        model.addAttribute("months", months);
        model.addAttribute("monthlyAmounts", monthlyAmounts);

        model.addAttribute("transactions", transactions);
        model.addAttribute("goals", goals);

        model.addAttribute("budgetCategories", budgetCategories);
        model.addAttribute("spentList", spentList);
        model.addAttribute("limitList", limitList);
        model.addAttribute("percentList", percentList);

        return "dashboard";
    }
}