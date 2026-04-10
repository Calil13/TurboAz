package org.example.turboaz.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static void accessDeniedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", 403);
        body.put("error", "Access Denied.");
        body.put("message", "Your role does not allow you to perform this operation.");

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }


    public static void unauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", 401);
        body.put("error", "Unauthorized");
        body.put("message", "Authentication required.");

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleNotFoundException(NotFoundException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "NOT_FOUND");
        body.put("message", e.getMessage());

        return new ExceptionDto(body);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleBadRequest(BadRequestException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad request.");
        body.put("message", e.getMessage());

        return new ExceptionDto(body);
    }

    @ExceptionHandler(OtpExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ExceptionDto handleExpiredOtpException(OtpExpiredException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.GONE.value());
        body.put("error", "OTP has expired");
        body.put("message", e.getMessage());
        return new ExceptionDto(body);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto alreadyExistsException(AlreadyExistsException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Already exists.");
        body.put("message", e.getMessage());

        return new ExceptionDto(body);
    }

    @ExceptionHandler(RoleNotMatchException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDto roleNotMatchException(RoleNotMatchException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Your role is not suitable.");
        body.put("message", e.getMessage());

        return new ExceptionDto(body);
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleWrongPassword(WrongPasswordException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Wrong password");
        body.put("message", e.getMessage());

        return new ExceptionDto(body);
    }
}
