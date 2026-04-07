package org.example.turboaz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.turboaz.validation.ValidPhone;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersUpdatePhoneDto {
    @ValidPhone
    String newPhone;
}