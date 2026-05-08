package org.example.turboaz.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.CarRequestDto;
import org.example.turboaz.dto.CarResponseDto;
import org.example.turboaz.entity.Car;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.mapper.CarMapper;
import org.example.turboaz.repository.CarRepository;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final UsersRepository usersRepository;
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public Page<CarResponseDto> getCars(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);
        return cars.map(carMapper::toDto);
    }

    public CarResponseDto getCar(Long id) {
        var car = carRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Car not found. \nCarID: {}", id);
                    return new NotFoundException("CAR_NOT_FOUND");
                });

        return carMapper.toDto(car);
    }

    public void addCar(CarRequestDto carRequestDto) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOND"));

        var car = carMapper.toEntity(carRequestDto);

        car.setUsers(user);
        car.setCreatedAt(LocalDateTime.now());
        car.setViewCount(0L);

        carRepository.save(car);
    }

    public void editCar(CarRequestDto carRequestDto, Long carId) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOND"));

        var car = carRepository.findByIdAndUsers(carId, user)
                .orElseThrow(() -> {
                    log.error("Car not found for this user. \nUserEmail: {}, \nCarID: {}", user.getEmail(), carId);
                    return new NotFoundException("CAR_NOT_FOUND_OR_ACCESS_DENIED");
                });

        carMapper.updateEntityFromDto(carRequestDto, car);
        carRepository.save(car);
    }
}
