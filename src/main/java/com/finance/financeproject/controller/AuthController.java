package com.finance.financeproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.finance.financeproject.model.user;
import com.finance.financeproject.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // ✅ Single constructor (correct way)
    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Show register page
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new user());
        return "register";
    }

    // Save user
    @PostMapping("/register")
    public String registerUser(@ModelAttribute user user) {

        System.out.print("USER: "+ user.getUsername());// debug

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encrypted
        userRepository.save(user);
        return "redirect:/login";
    }
}