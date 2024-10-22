package org.unibl.etf.ip.fitnessonline.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDto {
    @NotNull(message = "Content cannot be null")
    private String content;
}
