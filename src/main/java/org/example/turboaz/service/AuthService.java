package org.example.turboaz.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.EmailSentOtp;
import org.example.turboaz.dto.EmailVerifyOtpDto;
import org.example.turboaz.dto.RegisterFinishDto;
import org.example.turboaz.entity.Users;
import org.example.turboaz.enums.UsersRole;
import org.example.turboaz.exception.AlreadyExistsException;
import org.example.turboaz.exception.BadRequestException;
import org.example.turboaz.mapper.UsersMapper;
import org.example.turboaz.repository.OtpRepository;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final OtpRepository otpRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    
    public String startRegistration(EmailSentOtp sentOtp) {

        if (usersRepository.findByEmail(sentOtp.getEmail()).isPresent()) {
            log.info("Used email added: {}", sentOtp.getEmail());
            throw new AlreadyExistsException("Email is already in use!");
        }

        log.info("OTP sent to user: {}", sentOtp.getEmail());
        return otpService.sendOtp(sentOtp.getEmail());
    }

    public String verifyOtp(EmailVerifyOtpDto verifyOtp) {
        otpService.verifyOtp(verifyOtp.getEmail(), verifyOtp.getOtp());
        log.info("OTP verified.");
        return "OTP verified.";
    }

    public String finishRegister(RegisterFinishDto finishDto) {

        if (!otpService.isVerified(finishDto.getEmail())){
            log.error("OTP not verified!");
            throw new BadRequestException("OTP not verified!");
        }

        Users user = usersMapper.toEntity(finishDto);
        user.setPassword(passwordEncoder.encode(finishDto.getPassword()));
        user.setUserRole(UsersRole.USER);
        user.setPhone("+994" + finishDto.getPhone());

        usersRepository.save(user);
        otpService.removeOtp(finishDto.getEmail());

        log.info("New user registered. \nEmail: {}", finishDto.getEmail());
        return "Customer successfully registered.";
    }
}
