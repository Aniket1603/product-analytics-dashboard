package com.analytics.backend.dto;

public class FeatureAnalyticsDTO {

    private String featureName;
    private Long clickCount;

    public FeatureAnalyticsDTO(String featureName, Long clickCount) {
        this.featureName = featureName;
        this.clickCount = clickCount;
    }

    public String getFeatureName() {
        return featureName;
    }

    public Long getClickCount() {
        return clickCount;
    }
}