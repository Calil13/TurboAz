package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.CarRequestDto;
import org.example.turboaz.dto.CarResponseDto;
import org.example.turboaz.service.CarService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/car")
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public Page<CarResponseDto> getCars(@ParameterObject Pageable pageable) {
        return carService.getCars(pageable);
    }

    @GetMapping("{id}")
    public CarResponseDto getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PostMapping("/new-car")
    @SecurityRequirement(name = "bearerAuth")
    public void addCar(@Valid @RequestBody CarRequestDto carRequestDto) {
        carService.addCar(carRequestDto);
    }

    @PatchMapping("edit-car")
    @SecurityRequirement(name = "bearerAuth")
    public void editCar(@Valid @RequestBody CarRequestDto carRequestDto, Long carId) {
        carService.editCar(carRequestDto, carId);
    }
}
