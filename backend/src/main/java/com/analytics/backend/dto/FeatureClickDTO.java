package com.analytics.backend.dto;

public class FeatureClickDTO {

    private String featureName;
    private Integer clickCount;
    private String username;

    public FeatureClickDTO() {}

    public FeatureClickDTO(String featureName, Integer clickCount, String username) {
        this.featureName = featureName;
        this.clickCount = clickCount;
        this.username = username;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}