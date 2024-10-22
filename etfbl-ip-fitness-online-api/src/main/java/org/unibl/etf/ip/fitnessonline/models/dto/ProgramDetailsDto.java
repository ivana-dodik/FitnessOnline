package org.unibl.etf.ip.fitnessonline.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.unibl.etf.ip.fitnessonline.models.types.DifficultyLevel;

import java.util.List;

@Getter
@Setter
public class ProgramDetailsDto {
    List<AttributeNameValueDto> attributes;
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private DifficultyLevel difficultyLevel;
    private Integer durationMinutes;
    private String location;
    private Boolean deleted;
    private Boolean available;
    private String contactPhone;
    private Integer instructorId;
    private Integer categoryId;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Program Details:\n");
        stringBuilder.append("ID: ").append(id).append("\n");
        stringBuilder.append("Title: ").append(title).append("\n");
        stringBuilder.append("Description: ").append(description).append("\n");
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Difficulty Level: ").append(difficultyLevel).append("\n");
        stringBuilder.append("Duration: ").append(durationMinutes).append(" minutes\n");
        stringBuilder.append("Location: ").append(location).append("\n");
        stringBuilder.append("Deleted: ").append(deleted).append("\n");
        stringBuilder.append("Available: ").append(available).append("\n");
        stringBuilder.append("Contact Phone: ").append(contactPhone).append("\n");
        stringBuilder.append("Instructor ID: ").append(instructorId).append("\n");
        stringBuilder.append("Category ID: ").append(categoryId).append("\n");
        stringBuilder.append("Attributes:\n");
        if (attributes.isEmpty()) {
            stringBuilder.append("None.");
        } else {
            for (AttributeNameValueDto attribute : attributes) {
                stringBuilder.append("\t- ").append(attribute).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
