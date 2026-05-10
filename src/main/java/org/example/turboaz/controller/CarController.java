package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.car.CarRequestDto;
import org.example.turboaz.dto.car.CarResponseDto;
import org.example.turboaz.dto.car.CarUpdateDto;
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

    @PatchMapping("/edit-car")
    @SecurityRequirement(name = "bearerAuth")
    public void editCar(@Valid @RequestBody CarUpdateDto carUpdateDto, Long carId) {
        carService.editCar(carUpdateDto, carId);
    }

    @DeleteMapping("/delete-car/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
}
