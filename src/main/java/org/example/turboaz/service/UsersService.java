package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.UserResponseDto;
import org.example.turboaz.dto.UsersUpdateNameDto;
import org.example.turboaz.dto.UsersUpdatePhoneDto;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.mapper.UsersMapper;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public UserResponseDto getUserInfo() {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", currentEmail);
                    return new NotFoundException("USER_NOT_FOUND");
                });

        log.info("User info retrieved successfully for email: {}", currentEmail);
        return usersMapper.toDto(user);
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

        user.setPhone(updatePhoneDto.getNewPhone());
        usersRepository.save(user);

        log.info("User's phone updated successfully. \nUser ID: {}", user.getId());
        return "Phone updated successfully.";
    }
}
