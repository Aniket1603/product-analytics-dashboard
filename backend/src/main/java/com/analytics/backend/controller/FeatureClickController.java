package com.analytics.backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.backend.dto.DailyAnalyticsDTO;
import com.analytics.backend.dto.DashboardDTO;
import com.analytics.backend.dto.FeatureAnalyticsDTO;
import com.analytics.backend.dto.FeatureClickDTO;
import com.analytics.backend.model.FeatureClick;
import com.analytics.backend.service.FeatureClickService;

@RestController
@RequestMapping("/api/clicks")
@CrossOrigin(origins = "*")
public class FeatureClickController {

    private final FeatureClickService featureClickService;

    public FeatureClickController(FeatureClickService featureClickService) {
        this.featureClickService = featureClickService;
    }

    // ==============================
    // ✅ Track Feature Click
    // ==============================
    @PostMapping("/track")
    public ResponseEntity<?> trackFeature(@RequestBody FeatureClickDTO dto) {

        if (dto.getFeatureName() == null || dto.getFeatureName().isBlank()) {
            return ResponseEntity.badRequest().body("Feature name required");
        }

        FeatureClick click = new FeatureClick();
        click.setFeatureName(dto.getFeatureName());

        featureClickService.saveClick(click);

        return ResponseEntity.ok("Tracked");
    }

    // ==============================
    // ✅ Top Features
    // ==============================
    @GetMapping("/top")
    public ResponseEntity<List<FeatureClickDTO>> getTopFeatures(
            @RequestParam(defaultValue = "5") int limit) {

        return ResponseEntity.ok(
                featureClickService.getTopFeatures(limit)
        );
    }

    // ==============================
    // ✅ Dashboard Summary
    // ==============================
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard() {

        return ResponseEntity.ok(
                featureClickService.getDashboardStats()
        );
    }

    // ==============================
    // ✅ Daily Analytics
    // ==============================
    @GetMapping("/analytics/daily")
    public ResponseEntity<List<DailyAnalyticsDTO>> getDailyAnalytics() {

        return ResponseEntity.ok(
                featureClickService.getClicksPerDay()
        );
    }

    // ==============================
    // 🔥 SAFE Age + Gender + Date Filter
    // ==============================
    @GetMapping("/analytics/filter")
public ResponseEntity<List<FeatureAnalyticsDTO>> getFilteredAnalytics(

        @RequestParam(required = false) String start,
        @RequestParam(required = false) String end,
        @RequestParam(required = false) String age,
        @RequestParam(required = false) String gender) {

    LocalDateTime startDateTime = null;
    LocalDateTime endDateTime = null;

    try {
        if (start != null && !start.isBlank()) {
            startDateTime = LocalDate.parse(start).atStartOfDay();
        }

        if (end != null && !end.isBlank()) {
            endDateTime = LocalDate.parse(end).atTime(23, 59, 59);
        }
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(
            featureClickService.getFilteredAnalytics(
                    startDateTime,
                    endDateTime,
                    age,
                    gender
            )
    );
}
}