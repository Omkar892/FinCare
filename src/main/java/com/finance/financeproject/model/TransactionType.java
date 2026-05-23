package com.finance.financeproject.model;

import jakarta.persistence.*;

@Entity
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}