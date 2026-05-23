package com.finance.financeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.financeproject.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}