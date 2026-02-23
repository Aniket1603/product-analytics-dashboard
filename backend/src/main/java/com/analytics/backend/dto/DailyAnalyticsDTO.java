package com.analytics.backend.dto;

import java.time.LocalDate;

public class DailyAnalyticsDTO {

    private LocalDate date;
    private Long clicks;

    public DailyAnalyticsDTO(LocalDate date, Long clicks) {
        this.date = date;
        this.clicks = clicks;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getClicks() {
        return clicks;
    }
}