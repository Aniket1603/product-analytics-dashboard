package com.analytics.backend.dto;

public class DashboardDTO {

    private Long totalFeatures;
    private Long totalClicks;
    private FeatureClickDTO topFeature;

    public DashboardDTO(Long totalFeatures, Long totalClicks, FeatureClickDTO topFeature) {
        this.totalFeatures = totalFeatures;
        this.totalClicks = totalClicks;
        this.topFeature = topFeature;
    }

    public Long getTotalFeatures() {
        return totalFeatures;
    }

    public Long getTotalClicks() {
        return totalClicks;
    }

    public FeatureClickDTO getTopFeature() {
        return topFeature;
    }
}