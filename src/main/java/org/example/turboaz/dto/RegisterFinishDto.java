package org.example.turboaz.dto;


import lombok.Data;
import org.example.turboaz.validation.ValidEmail;
import org.example.turboaz.validation.ValidPassword;
import org.example.turboaz.validation.ValidPhone;

@Data
public class RegisterFinishDto {
    @ValidEmail
    private String email;

    private String name;

    @ValidPhone
    private String phone;
}