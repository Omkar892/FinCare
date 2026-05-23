package com.finance.financeproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

import com.finance.financeproject.model.Goal;
import com.finance.financeproject.repository.GoalRepository;

@Controller
@RequestMapping("/goals")
public class GoalController {

    private final GoalRepository goalRepository;

    // ✅ Constructor Injection
    public GoalController(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    // ================= SHOW GOAL PAGE =================
    @GetMapping("/add")
    public String showForm(Model model) {

        // form object
        model.addAttribute("goal", new Goal());

        // list of goals
        List<Goal> goals = goalRepository.findAll();
        model.addAttribute("goals", goals);

        return "add-goal";
    }

    // ================= SAVE GOAL =================
    @PostMapping("/save")
    public String saveGoal(@ModelAttribute Goal goal) {

        // set default saved amount
        if (goal.getSavedAmount() == null) {
            goal.setSavedAmount(0.0);
        }

        goalRepository.save(goal);

        return "redirect:/goals/add";
    }

    // ================= ADD AMOUNT =================
    @PostMapping("/addAmount")
    public String addAmount(@RequestParam Long goalId,
                            @RequestParam Double amount) {

        Goal goal = goalRepository.findById(goalId)
                .orElse(null);

        if (goal != null) {

            Double current = goal.getSavedAmount() == null ? 0.0 : goal.getSavedAmount();
            Double target = goal.getTargetAmount() == null ? 0.0 : goal.getTargetAmount();

            Double newAmount = current + amount;

            // prevent exceeding target
            if (newAmount > target) {
                newAmount = target;
            }

            goal.setSavedAmount(newAmount);
            goalRepository.save(goal);
        }

        return "redirect:/goals/add";
    }
    @PostMapping("/removeAmount")
public String removeAmount(@RequestParam Long goalId,
                           @RequestParam Double amount) {

    Goal goal = goalRepository.findById(goalId).orElse(null);

    if (goal != null) {

        Double current = goal.getSavedAmount() == null ? 0.0 : goal.getSavedAmount();
        Double newAmount = current - amount;

        // prevent negative
        if (newAmount < 0) {
            newAmount = 0.0;
        }

        goal.setSavedAmount(newAmount);
        goalRepository.save(goal);
    }

    return "redirect:/goals/add";
}

    // ================= DELETE GOAL (BONUS FEATURE) =================
    @GetMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {

        goalRepository.deleteById(id);

        return "redirect:/goals/add";
    }
}