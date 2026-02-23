package com.analytics.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class FeatureClickDTO {

    @NotBlank(message = "Feature name cannot be empty")
    @Size(min = 2, max = 50, message = "Feature name must be between 2 and 50 characters")
    private String featureName;

    private Integer clickCount;

    private LocalDateTime timestamp;

    // ✅ Default constructor (Required)
    public FeatureClickDTO() {
    }

    // ✅ Constructor for Response Mapping
    public FeatureClickDTO(String featureName, Integer clickCount, LocalDateTime timestamp) {
        this.featureName = featureName;
        this.clickCount = clickCount;
        this.timestamp = timestamp;
    }

    // ✅ Getters & Setters

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}