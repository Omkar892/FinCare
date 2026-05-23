package com.finance.financeproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance.financeproject.model.Transaction;
import com.finance.financeproject.model.TransactionCategory;
import com.finance.financeproject.model.user;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 🔹 Get transactions by user
    List<Transaction> findByUser(user user);

    // 🔹 Total income
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'INCOME' AND t.user = :user")
    Double getTotalIncomeByUser(@Param("user") user user);

    // 🔹 Total expense
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' AND t.user = :user")
    Double getTotalExpenseByUser(@Param("user") user user);

    // 🔹 Category-wise expenses (global)
    @Query("SELECT t.category.name, SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' GROUP BY t.category.name")
    List<Object[]> getCategoryWiseExpenses();

    // 🔹 Monthly expenses (global)
    @Query("SELECT MONTH(t.date), SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' GROUP BY MONTH(t.date)")
    List<Object[]> getMonthlyExpenses();

    // 🔹 Category-wise expenses (user-specific)
    @Query("SELECT t.category.name, SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' AND t.user = :user GROUP BY t.category.name")
    List<Object[]> getCategoryWiseExpensesByUser(@Param("user") user user);

    // 🔹 Monthly expenses (user-specific)
    @Query("SELECT MONTH(t.date), SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' AND t.user = :user GROUP BY MONTH(t.date)")
    List<Object[]> getMonthlyExpensesByUser(@Param("user") user user);

    // 🔹 Expense by category
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.category = :category AND t.type = 'EXPENSE'")
    Double getTotalExpenseByUserAndCategory(@Param("user") user user,
                                            @Param("category") TransactionCategory category);

    // 🔥 Budget usage (MAIN FEATURE)
    @Query("""
        SELECT t.category.name, SUM(t.amount), b.amountLimit
        FROM Transaction t
        JOIN Budget b ON t.category.id = b.category.id
        WHERE t.user = :user AND t.type = 'EXPENSE'
        GROUP BY t.category.name, b.amountLimit
    """)
    List<Object[]> getBudgetUsage(@Param("user") user user);
}