package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.*;
import org.example.turboaz.entity.RefreshToken;
import org.example.turboaz.entity.Users;
import org.example.turboaz.enums.UsersRole;
import org.example.turboaz.exception.*;
import org.example.turboaz.jwt.JwtUtil;
import org.example.turboaz.mapper.UsersMapper;
import org.example.turboaz.repository.OtpRepository;
import org.example.turboaz.repository.RefreshTokenRepository;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final OtpRepository otpRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    
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

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        var user = usersRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found. Please register first."));

        if (user.getUserRole().equals(UsersRole.ADMIN)){
            log.error("Admin must log in using the 'admin/login' method.");
            throw new RoleNotMatchException("LOGIN_ERROR");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            log.error("Password is Wrong!");
            throw new WrongPasswordException("Password is Wrong!");
        }

        refreshTokenRepository.deleteByUser(user);

        String accessToken = jwtUtil.generateAccessToken(loginRequestDto.getEmail());
        String refreshTokenStr = jwtUtil.generateRefreshToken();

        var refreshToken = RefreshToken.builder()
                .token(refreshTokenStr)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(30))
                .build();

        refreshTokenRepository.save(refreshToken);

        log.info("User login. \nUser ID: {}", user.getId());
        return new AuthResponseDto(accessToken, refreshTokenStr);
    }
}
