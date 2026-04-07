package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.UserResponseDto;
import org.example.turboaz.dto.UsersUpdateNameDto;
import org.example.turboaz.dto.UsersUpdatePhoneDto;
import org.example.turboaz.service.UsersService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public UserResponseDto getUserInfo() {
        return usersService.getUserInfo();
    }

    @PatchMapping("/update-name")
    @SecurityRequirement(name = "bearerAuth")
    public String updateName(@RequestBody UsersUpdateNameDto updateNameDto) {
         return usersService.updateName(updateNameDto);
    }

    @PatchMapping("/update-phone")
    @SecurityRequirement(name = "bearerAuth")
    public String updatePhone(@RequestBody UsersUpdatePhoneDto updatePhoneDto) {
        return usersService.updatePhone(updatePhoneDto);
    }
}
