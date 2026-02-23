package com.analytics.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.analytics.backend.dto.FeatureAnalyticsDTO;
import com.analytics.backend.model.FeatureClick;

public interface FeatureClickRepository extends JpaRepository<FeatureClick, Long> {

    // ✅ Top Features (Grouped by featureName)
    @Query("""
        SELECT f.featureName, COUNT(f)
        FROM FeatureClick f
        GROUP BY f.featureName
        ORDER BY COUNT(f) DESC
    """)
    List<Object[]> findTopFeatures();


    // ✅ Clicks Per Day
    @Query("""
        SELECT DATE(f.timestamp), COUNT(f)
        FROM FeatureClick f
        GROUP BY DATE(f.timestamp)
        ORDER BY DATE(f.timestamp)
    """)
    List<Object[]> getClicksPerDay();


    // ✅ Date Range Analytics
    @Query("""
        SELECT DATE(f.timestamp), COUNT(f)
        FROM FeatureClick f
        WHERE f.timestamp BETWEEN :start AND :end
        GROUP BY DATE(f.timestamp)
        ORDER BY DATE(f.timestamp)
    """)
    List<Object[]> getClicksBetweenDates(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

@Query("""
SELECT new com.analytics.backend.dto.FeatureAnalyticsDTO(
    f.featureName,
    COUNT(f)
)
FROM FeatureClick f
JOIN f.user u
WHERE 
    (f.timestamp >= COALESCE(:start, f.timestamp))
    AND (f.timestamp <= COALESCE(:end, f.timestamp))
    AND (:gender IS NULL OR :gender = '' OR LOWER(u.gender) = LOWER(:gender))
    AND (
        :age IS NULL OR :age = '' OR
        (:age = '<18' AND u.age < 18) OR
        (:age = '18-40' AND u.age BETWEEN 18 AND 40) OR
        (:age = '>40' AND u.age > 40)
    )
GROUP BY f.featureName
ORDER BY COUNT(f) DESC
""")
List<FeatureAnalyticsDTO> getFilteredAnalytics(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("age") String age,
        @Param("gender") String gender
);
}