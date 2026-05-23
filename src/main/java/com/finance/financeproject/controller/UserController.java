package com.finance.financeproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.financeproject.model.user;
import com.finance.financeproject.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public user register(@RequestBody user user) {
        return userService.registeruser(user);
    }

    @GetMapping
    public List<user> getAllUsers() {
        return userService.getAllUsers();
    }
}
