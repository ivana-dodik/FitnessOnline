package org.unibl.etf.ip.fitnessonline.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogDto {
    private String username;
    private String dateTime;
    private String exerciseType;
    private Integer durationMinutes;
    private String intensity;
    private String results;
}
