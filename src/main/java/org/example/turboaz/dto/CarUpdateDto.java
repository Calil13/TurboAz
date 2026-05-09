package org.example.turboaz.dto;

import lombok.Data;
import org.example.turboaz.enums.*;

import java.math.BigDecimal;

@Data
public class CarUpdateDto {

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
}