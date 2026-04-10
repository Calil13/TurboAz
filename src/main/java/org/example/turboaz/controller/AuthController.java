package org.example.turboaz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.*;
import org.example.turboaz.service.AuthService;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/start")
    public String startRegistration(@Valid @RequestBody EmailSentOtp sentOtp) {
        return authService.startRegistration(sentOtp);
    }

    @PostMapping("/register/verify")
    public String verifyOtp(@Valid @RequestBody EmailVerifyOtpDto verifyOtp) {
        return authService.verifyOtp(verifyOtp);
    }

    @PostMapping("/register/finish")
    public String finishRegister(@Valid @RequestBody RegisterFinishDto finishDto) {
        return authService.finishRegister(finishDto);
    }

    @PostMapping
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
