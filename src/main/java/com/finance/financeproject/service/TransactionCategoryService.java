package com.finance.financeproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.financeproject.model.TransactionCategory;
import com.finance.financeproject.repository.TransactionCategoryRepository;

@Service
public class TransactionCategoryService {

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    public TransactionCategory saveCategory(TransactionCategory category) {
        return categoryRepository.save(category);
    }

    public List<TransactionCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
}
