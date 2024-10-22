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
public class AddUserMessageDto {
    @NotNull(message = "Message must have content")
    String content;
    @NotNull(message = "Receiver ID must be specified")
    Integer receiverId;
}
