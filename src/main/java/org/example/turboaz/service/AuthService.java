package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.EmailSentOtp;
import org.example.turboaz.dto.EmailVerifyOtpDto;
import org.example.turboaz.exception.AlreadyExistsException;
import org.example.turboaz.repository.OtpRepository;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsersRepository usersRepository;
    private final OtpRepository otpRepository;
    private final OtpService otpService;
    
    public String startRegistration(EmailSentOtp sentOtp) {

        if (usersRepository.findByEmail(sentOtp.getEmail()).isPresent()) {
            log.info("Used email added: {}", sentOtp.getEmail());
            throw new AlreadyExistsException("Email is already in use!");
        }

        log.info("OTP sent to user: {}", sentOtp.getEmail());
        return otpService.sendOtp(sentOtp.getEmail());
    }

    public String verifyOtp(EmailVerifyOtpDto verifyOtp) {

    }
}
