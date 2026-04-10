package org.example.turboaz.dto;

import lombok.Data;
import org.example.turboaz.validation.ValidEmail;

@Data
public class EmailSentOtp {
    @ValidEmail
    private String email;
}
