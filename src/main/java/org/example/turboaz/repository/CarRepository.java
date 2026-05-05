package org.example.turboaz.repository;

import org.example.turboaz.entity.Car;
import org.example.turboaz.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
