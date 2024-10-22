package org.unibl.etf.ip.fitnessonline.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity_log")
public class ActivityLogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "exercise_type", nullable = false, length = 45)
    private String exerciseType;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "intensity", nullable = false, length = 45)
    private String intensity;

    @Column(name = "results", nullable = false)
    private Double results;

    @Column(name = "log_date", nullable = false)
    private Timestamp logDate;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

}
