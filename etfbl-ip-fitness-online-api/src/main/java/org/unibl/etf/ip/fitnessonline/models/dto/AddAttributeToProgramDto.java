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
public class AddAttributeToProgramDto {
    @NotNull(message = "Attribute id cannot be null")
    private Integer attributeId;

    @NotNull(message = "Attribute value cannot be null")
    private String value;
}
