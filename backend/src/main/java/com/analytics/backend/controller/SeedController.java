package com.analytics.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.backend.model.FeatureClick;
import com.analytics.backend.model.User;
import com.analytics.backend.repository.FeatureClickRepository;
import com.analytics.backend.repository.UserRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/seed")
public class SeedController {

    private final UserRepository userRepository;
    private final FeatureClickRepository featureClickRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SeedController(UserRepository userRepository,
                          FeatureClickRepository featureClickRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.featureClickRepository = featureClickRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String seedData() {

        featureClickRepository.deleteAll();
        userRepository.deleteAll();

        // 🔥 Create Users
        User u1 = new User("rahul", passwordEncoder.encode("1234"), 17, "Male");
        User u2 = new User("priya", passwordEncoder.encode("1234"), 25, "Female");
        User u3 = new User("amit", passwordEncoder.encode("1234"), 35, "Male");
        User u4 = new User("sneha", passwordEncoder.encode("1234"), 45, "Female");
        User u5 = new User("alex", passwordEncoder.encode("1234"), 52, "Other");

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5));

        List<User> users = userRepository.findAll();

        List<String> features = List.of(
                "Login",
                "Signup",
                "Search",
                "Profile Update",
                "Checkout",
                "Add to Cart"
        );

        Random random = new Random();

        // 🔥 Generate 30 days data
        for (int day = 0; day < 30; day++) {

            LocalDateTime baseDate = LocalDateTime.now().minusDays(day);

            for (User user : users) {

                int clicksPerDay = 10 + random.nextInt(20); // 10-30 clicks

                for (int i = 0; i < clicksPerDay; i++) {

                    FeatureClick click = new FeatureClick();
                    click.setUser(user);
                    click.setFeatureName(
                            features.get(random.nextInt(features.size()))
                    );
                    click.setTimestamp(
                            baseDate.minusHours(random.nextInt(24))
                    );

                    featureClickRepository.save(click);
                }
            }
        }

        return "🔥 30 Days Analytics Data Seeded Successfully!";
    }
}