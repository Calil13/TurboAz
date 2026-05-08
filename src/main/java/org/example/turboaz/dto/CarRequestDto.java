package org.example.turboaz.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.turboaz.enums.*;
import java.math.BigDecimal;

@Data
public class CarRequestDto {

    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "City must be selected")
    private String city;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Body type is required")
    private BodyType bodyType;

    @NotNull(message = "Door count is required")
    private DoorCount doorCount;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Color is required")
    private Color color;

    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @NotNull(message = "Drive type is required")
    private DriveType driveType;

    @NotNull(message = "Transmission is required")
    private Transmission transmission;

    @NotNull(message = "Engine capacity is required")
    private EngineCapacity engineCapacity;

    @NotNull(message = "Horsepower is required")
    @Positive(message = "Horsepower must be a positive number")
    private Integer power;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Double mileage;
}