package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.*;
import org.example.turboaz.service.UsersService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/user-info/public/{id}")
    public UserResponseDtoPublic getUserInfoPublic(@PathVariable Long id) {
        return usersService.getUserInfoPublic(id);
    }

    @GetMapping("/user-info/private")
    @SecurityRequirement(name = "bearerAuth")
    public UserResponseDtoPrivate getUserInfoPrivate() {
        return usersService.getUserInfoPrivate();
    }

    @PatchMapping("/update/name")
    @SecurityRequirement(name = "bearerAuth")
    public String updateName(@RequestBody UsersUpdateNameDto updateNameDto) {
         return usersService.updateName(updateNameDto);
    }

    @PatchMapping("/update/phone")
    @SecurityRequirement(name = "bearerAuth")
    public String updatePhone(@RequestBody UsersUpdatePhoneDto updatePhoneDto) {
        return usersService.updatePhone(updatePhoneDto);
    }

    @PatchMapping("/update/password")
    @SecurityRequirement(name = "bearerAuth")
    public String updatePassword(@Valid @RequestBody UsersUpdatePasswordRequestDto updatePassword) {
        return usersService.updatePassword(updatePassword);
    }
}
