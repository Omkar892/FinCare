package com.finance.financeproject.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String type;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @PrePersist
    public void setDateIfNull() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private TransactionCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;
}