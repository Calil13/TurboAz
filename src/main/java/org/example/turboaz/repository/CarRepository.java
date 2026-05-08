package org.example.turboaz.repository;

import org.example.turboaz.entity.Car;
import org.example.turboaz.entity.OtpCode;
import org.example.turboaz.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByIdAndUsers(Long id, Users users);
}
