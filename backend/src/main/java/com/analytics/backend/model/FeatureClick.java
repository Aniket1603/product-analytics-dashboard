package com.analytics.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_clicks")
public class FeatureClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String featureName;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public FeatureClick() {}

    public FeatureClick(String featureName, LocalDateTime timestamp, User user) {
        this.featureName = featureName;
        this.timestamp = timestamp;
        this.user = user;
    }

    public Long getId() { return id; }

    public String getFeatureName() { return featureName; }

    public void setFeatureName(String featureName) { this.featureName = featureName; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}