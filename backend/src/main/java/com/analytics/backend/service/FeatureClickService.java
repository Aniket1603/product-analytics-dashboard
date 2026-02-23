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
import com.analytics.backend.repository.FeatureClickRepository;

@Service
public class FeatureClickService {

    private final FeatureClickRepository featureClickRepository;

    public FeatureClickService(FeatureClickRepository featureClickRepository) {
        this.featureClickRepository = featureClickRepository;
    }

    public FeatureClick saveClick(FeatureClick click) {
        click.setTimestamp(LocalDateTime.now());
        return featureClickRepository.save(click);
    }

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

    public List<DailyAnalyticsDTO> getClicksPerDay() {

        List<Object[]> results = featureClickRepository.getClicksPerDay();

        return results.stream()
                .map(obj -> {

                    LocalDate date = null;

                    if (obj[0] instanceof java.sql.Date sqlDate) {
                        date = sqlDate.toLocalDate();
                    }

                    Long clicks = ((Number) obj[1]).longValue();

                    return new DailyAnalyticsDTO(date, clicks);
                })
                .toList();
    }

    public List<FeatureAnalyticsDTO> getFilteredAnalytics(
            LocalDateTime start,
            LocalDateTime end,
            String age,
            String gender) {

        return featureClickRepository.getFilteredAnalytics(
                start,
                end,
                age,
                gender
        );
    }
}