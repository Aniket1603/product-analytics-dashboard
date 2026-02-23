package com.analytics.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.analytics.backend.model.User;
import com.analytics.backend.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    // ✅ Manual Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}