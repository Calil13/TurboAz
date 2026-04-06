package org.example.turboaz.controller;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.UserResponseDto;
import org.example.turboaz.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public UserResponseDto getUserInfo() {
        return null;
    }
}
