package org.unibl.etf.ip.fitnessonline.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unibl.etf.ip.fitnessonline.models.types.DifficultyLevel;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProgramDto {
    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "Difficulty level cannot be null")
    private DifficultyLevel difficultyLevel;

    @NotNull(message = "Duration cannot be null")
    private Integer durationMinutes;

    @NotNull(message = "Location cannot be null")
    private String location;

    @NotNull(message = "Contact phone cannot be null")
    private String contactPhone;

    @NotNull(message = "Program category cannot be null")
    private Integer categoryId;

    //@NotNull(message = "Date cannot be null")
    //private Timestamp dateCreated;

    private AddAttributeToProgramDto[] attributes;
}
