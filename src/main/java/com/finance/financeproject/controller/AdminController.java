package com.finance.financeproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.finance.financeproject.model.user;
import com.finance.financeproject.repository.UserRepository;
// import com.finance.financeproject.model.user;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Show all users
    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/change-role/{id}")
public String changeRole(@PathVariable Long id) {

    user u = userRepository.findById(id).orElse(null);

    if (u != null) {
        if (u.getRole().equals("ADMIN")) {
            u.setRole("USER");
        } else {
            u.setRole("ADMIN");
        }
        userRepository.save(u);
    }

    return "redirect:/admin/users";
}
}