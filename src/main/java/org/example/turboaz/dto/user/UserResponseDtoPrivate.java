package org.example.turboaz.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDtoPrivate {

    private String name;
    private String email;
    private String phone;
}
