package org.unibl.etf.ip.fitnessonline.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.fitnessonline.models.types.DifficultyLevel;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "program")
public class ProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 225)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    private DifficultyLevel difficultyLevel;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "location", nullable = false, length = 45)
    private String location;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "contact_phone", nullable = false, length = 45)
    private String contactPhone;

    @Column(name = "instructor_id", nullable = false)
    private Integer instructorId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "time_created", nullable = false)
    private Timestamp timeCreated;

}
