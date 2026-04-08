package org.example.turboaz.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.turboaz.validation.ValidEmail;

@Data
public class EmailSentOtp {
    @ValidEmail
    private String email;
}
