package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.CarCreateRequest;
import org.example.turboaz.service.CarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/car")
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/new-car")
    @SecurityRequirement(name = "bearerAuth")
    public void addCar(@Valid @RequestBody CarCreateRequest carCreateRequest) {
        carService.addCar(carCreateRequest);
    }
}
