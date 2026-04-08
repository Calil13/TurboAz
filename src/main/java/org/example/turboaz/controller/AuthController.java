package org.example.turboaz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.EmailSentOtp;
import org.example.turboaz.service.AuthService;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public String startRegistration(@Valid @RequestBody EmailSentOtp sentOtp) {
        return null;
    }
}
