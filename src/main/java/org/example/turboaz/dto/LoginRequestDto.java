package org.example.turboaz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.turboaz.validation.ValidEmail;
import org.example.turboaz.validation.ValidPassword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}