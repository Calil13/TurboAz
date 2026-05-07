package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.CarCreateRequest;
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

    public void addCar(CarCreateRequest carCreateRequest) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOND"));

        var car = carMapper.toEntity(carCreateRequest);

        car.setUsers(user);
        car.setCreatedAt(LocalDateTime.now());
        car.setViewCount(0L);

        carRepository.save(car);
    }
}
