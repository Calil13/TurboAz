package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.CarCreateRequest;
import org.example.turboaz.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/car")
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public Page<>

    @PostMapping("/new-car")
    @SecurityRequirement(name = "bearerAuth")
    public void addCar(@Valid @RequestBody CarCreateRequest carCreateRequest) {
        carService.addCar(carCreateRequest);
    }
}
