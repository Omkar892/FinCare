package com.finance.financeproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finance.financeproject.model.user;
import com.finance.financeproject.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public user registeruser(user user) {
        return userRepository.save(user);
    }

    public List<user> getAllUsers() {
        return userRepository.findAll();
    }
}
