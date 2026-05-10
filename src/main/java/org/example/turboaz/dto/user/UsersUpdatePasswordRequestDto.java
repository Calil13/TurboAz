package org.example.turboaz.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.turboaz.validation.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersUpdatePasswordRequestDto {
    private String currentPassword;

    @ValidPassword
    private String newPassword;
    private String confirmNewPassword;
}