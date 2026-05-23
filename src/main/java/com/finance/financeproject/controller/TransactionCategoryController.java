package com.finance.financeproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.financeproject.model.TransactionCategory;
import com.finance.financeproject.service.TransactionCategoryService;

@RestController
@RequestMapping("/categories")
public class TransactionCategoryController {

    @Autowired
    private TransactionCategoryService categoryService;

    @PostMapping
    public TransactionCategory createCategory(@RequestBody TransactionCategory category) {
        return categoryService.saveCategory(category);
    }

    @GetMapping
    public List<TransactionCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
