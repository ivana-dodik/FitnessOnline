package org.unibl.etf.ip.fitnessonline.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDetailsDto {
    private Integer id;
    private String content;
    private Boolean isRead;
    private Timestamp dateTime;
    private Integer senderId;
}