package org.example.turboaz.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.turboaz.entity.Users;
import org.example.turboaz.enums.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDto {

    private Long viewCount = 0L;
    private String brand;
    private String model;
    private String city;
    private BigDecimal price;
    private BodyType bodyType;
    private DoorCount doorCount;
    private Integer year;
    private Color color;
    private FuelType fuelType;
    private DriveType driveType;
    private Transmission transmission;
    private EngineCapacity engineCapacity;
    private Integer power;
    private Double mileage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
