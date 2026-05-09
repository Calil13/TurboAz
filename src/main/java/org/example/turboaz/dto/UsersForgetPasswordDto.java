package org.example.turboaz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.turboaz.validation.ValidEmail;
import org.example.turboaz.validation.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersForgetPasswordDto {

    @ValidEmail
    private String email;

    @ValidPassword
    private String newPassword;
    private String confirmNewPassword;
}