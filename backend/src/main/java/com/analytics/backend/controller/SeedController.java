package com.analytics.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.backend.model.User;
import com.analytics.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/seed")
public class SeedController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String seedUser() {

        // Check if admin already exists
        if (userRepository.findByUsername("admin") != null) {
            return "Admin user already exists";
        }

        // 🔥 Direct object create (No Spring Bean needed)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("admin123"));

        userRepository.save(user);

        return "Admin user created successfully";
    }
}