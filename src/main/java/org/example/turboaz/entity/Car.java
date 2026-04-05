package org.example.turboaz.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.enums.*;

import java.math.BigDecimal;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoorCount doorCount;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Color color;

    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private DriveType driveType;
}
