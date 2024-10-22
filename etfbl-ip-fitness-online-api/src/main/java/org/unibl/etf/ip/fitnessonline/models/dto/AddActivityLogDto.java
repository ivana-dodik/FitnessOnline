package org.unibl.etf.ip.fitnessonline.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddActivityLogDto {
    private String exerciseType;

    private Integer durationMinutes;

    private String intensity;

    private Double results;
}
