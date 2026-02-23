package com.analytics.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.analytics.backend.dto.DailyAnalyticsDTO;
import com.analytics.backend.dto.DashboardDTO;
import com.analytics.backend.dto.FeatureAnalyticsDTO;
import com.analytics.backend.dto.FeatureClickDTO;
import com.analytics.backend.model.FeatureClick;
import com.analytics.backend.model.User;
import com.analytics.backend.repository.FeatureClickRepository;
import com.analytics.backend.repository.UserRepository;

@Service
public class FeatureClickService {

    private final FeatureClickRepository featureClickRepository;
    private final UserRepository userRepository;

    public FeatureClickService(
            FeatureClickRepository featureClickRepository,
            UserRepository userRepository) {

        this.featureClickRepository = featureClickRepository;
        this.userRepository = userRepository;
    }

    // ✅ Save Click
    public FeatureClick saveClick(FeatureClick click) {

        click.setTimestamp(LocalDateTime.now());

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No users found"));

        click.setUser(user);

        return featureClickRepository.save(click);
    }

    // ✅ Top Features
    public List<FeatureClickDTO> getTopFeatures(int limit) {

        List<Object[]> results = featureClickRepository.findTopFeatures();

        return results.stream()
                .limit(limit)
                .map(obj -> new FeatureClickDTO(
                        (String) obj[0],
                        ((Number) obj[1]).intValue(),
                        null
                ))
                .toList();
    }

    // ✅ Dashboard Summary
    public DashboardDTO getDashboardStats() {

        Long totalClicks = featureClickRepository.count();

        List<Object[]> topResults = featureClickRepository.findTopFeatures();

        FeatureClickDTO topDTO = null;

        if (!topResults.isEmpty()) {
            Object[] top = topResults.get(0);

            topDTO = new FeatureClickDTO(
                    (String) top[0],
                    ((Number) top[1]).intValue(),
                    null
            );
        }

        return new DashboardDTO(
                totalClicks,
                totalClicks,
                topDTO
        );
    }

    // ✅ Daily Analytics
    public List<DailyAnalyticsDTO> getClicksPerDay() {

        List<Object[]> results = featureClickRepository.getClicksPerDay();

        return results.stream()
                .map(obj -> {

                    LocalDate date = null;

                    if (obj[0] instanceof java.sql.Date sqlDate) {
                        date = sqlDate.toLocalDate();
                    } else if (obj[0] instanceof LocalDate localDate) {
                        date = localDate;
                    }

                    Long clicks = ((Number) obj[1]).longValue();

                    return new DailyAnalyticsDTO(date, clicks);
                })
                .toList();
    }

    // 🔥 FIXED FILTER LOGIC
    public List<FeatureAnalyticsDTO> getFilteredAnalytics(
            LocalDateTime start,
            LocalDateTime end,
            String age,
            String gender) {

        boolean noDate = (start == null && end == null);
        boolean noAge = (age == null || age.isBlank());
        boolean noGender = (gender == null || gender.isBlank());

        // 🔥 If no filters selected → return top features
        if (noDate && noAge && noGender) {

            List<Object[]> results = featureClickRepository.findTopFeatures();

            return results.stream()
                    .map(obj -> new FeatureAnalyticsDTO(
                            (String) obj[0],
                            ((Number) obj[1]).longValue()
                    ))
                    .toList();
        }

        return featureClickRepository.getFilteredAnalytics(
                start, end, age, gender
        );
    }
}