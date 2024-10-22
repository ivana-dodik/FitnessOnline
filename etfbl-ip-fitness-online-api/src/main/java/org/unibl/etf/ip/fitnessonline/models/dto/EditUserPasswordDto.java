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
public class EditUserPasswordDto {
    @NotNull(message = "Current password cannot be null")
    private String currentPassword;

    @NotNull(message = "New password cannot be null")
    private String newPassword;
}
