package org.unibl.etf.ip.fitnessonline.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.unibl.etf.ip.fitnessonline.models.types.PaymentMethod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddParticipationDto {
    @NotNull(message = "Program id cannot be null")
    private Integer programId;

    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Payment details cannot be null")
    @Length(min = 5, message = "Payment details must be at least 5 characters long")
    private String paymentDetails;
}
