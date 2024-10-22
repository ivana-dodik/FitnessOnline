package org.unibl.etf.ip.fitnessonline.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserProfileDto {
    @NotNull(message = "First name cannot be null")
    String firstName;

    @NotNull(message = "Last name cannot be null")
    String lastName;

    @NotNull(message = "City cannot be null")
    String city;

    @Email(message = "Please enter email in valid format")
    @NotNull(message = "First name cannot be null")
    String email;
}
