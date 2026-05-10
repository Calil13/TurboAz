package org.example.turboaz.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDtoPublic {

    private String name;
    private String phone;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
}
