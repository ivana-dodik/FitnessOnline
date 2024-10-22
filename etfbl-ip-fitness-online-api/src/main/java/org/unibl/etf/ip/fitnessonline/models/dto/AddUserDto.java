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
public class AddUserDto {
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "City cannot be null")
    private String city;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be properly formatted")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    private String avatarUrl;

    private String captchaResponse;

    private int answer; // salje se sa front-end pri svakom zahtjevu
}
