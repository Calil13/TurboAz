package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.user.*;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.exception.WrongPasswordException;
import org.example.turboaz.mapper.UsersMapper;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDtoPublic getUserInfoPublic(Long id) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found for id: {}", id);
                    return new NotFoundException("USER_NOT_FOUND");
                });

        log.info("User info(Public) retrieved successfully for id: {}", id);
        return usersMapper.toDtoPublic(user);
    }

    public UserResponseDtoPrivate getUserInfoPrivate() {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", currentEmail);
                    return new NotFoundException("USER_NOT_FOUND");
                });

        log.info("User info retrieved successfully for email: {}", currentEmail);
        return usersMapper.toDtoPrivate(user);
    }

    public String updateName(UsersUpdateNameDto updateNameDto) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", currentEmail);
                    return new NotFoundException("USER_NOT_FOUND");
                });

        user.setName(updateNameDto.getName());
        usersRepository.save(user);

        log.info("User's name updated successfully. \nUser ID: {}", user.getId());
        return "Name updated successfully.";
    }

    public String updatePhone(UsersUpdatePhoneDto updatePhoneDto) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", currentEmail);
                    return new NotFoundException("USER_NOT_FOUND");
                });

        user.setPhone("+994" + updatePhoneDto.getNewPhone());
        usersRepository.save(user);

        log.info("User's phone updated successfully. \nUser ID: {}", user.getId());
        return "Phone updated successfully.";
    }

    public String updatePassword(UsersUpdatePasswordRequestDto updatePassword) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(updatePassword.getCurrentPassword(), user.getPassword())) {
            log.warn("Password update failed: existing password is incorrect. User email: {}", user.getEmail());
            throw new WrongPasswordException("Existing Password is wrong!");
        }

        if (!updatePassword.getNewPassword().equals(updatePassword.getConfirmNewPassword())) {
            log.warn("Password update failed: new password and confirm password do not match. User email: {}", user.getEmail());
            throw new WrongPasswordException("Confirm new password correctly!");
        }

        if (passwordEncoder.matches(updatePassword.getNewPassword(), user.getPassword())) {
            log.warn("Password update failed: new password is the same as the current password. User email: {}", user.getEmail());
            throw new WrongPasswordException("New password cannot be same as the existing password!");
        }

        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        usersRepository.save(user);

        log.info("User's password updated successfully. \nUser ID: {}", user.getId());
        return "Password changed successfully.";
    }
}
