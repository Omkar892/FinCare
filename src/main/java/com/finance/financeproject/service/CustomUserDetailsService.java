package com.finance.financeproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

import com.finance.financeproject.model.user;
import com.finance.financeproject.repository.UserRepository;

//import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

@Override
public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {

    user user = userRepository.findByEmail(input)
            .orElseGet(() -> userRepository.findByUsername(input).orElse(null));

    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }

    return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
    );
}}