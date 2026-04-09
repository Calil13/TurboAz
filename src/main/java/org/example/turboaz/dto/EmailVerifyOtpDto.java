package org.example.turboaz.dto;

import lombok.Data;
import org.example.turboaz.validation.ValidEmail;

@Data
public class EmailVerifyOtpDto {
    @ValidEmail
    private String email;

    private String otp;
}