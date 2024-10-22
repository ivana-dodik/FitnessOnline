package org.unibl.etf.ip.fitnessonline.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class AttributeNameValueDto {
    private String name;
    private String value;

    @Override
    public String toString() {
        return "Attribute: " + name + " - " + value;
    }
}
