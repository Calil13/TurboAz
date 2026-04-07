package org.example.turboaz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.turboaz.enums.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "view_count")
    private Long viewCount = 0L;

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
    @Column(name = "door_count", nullable = false, length = 50)
    private DoorCount doorCount;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Color color;

    @Column(nullable = false)
    private FuelType fuelType;

    @Column(name = "driver_type", nullable = false, length = 50)
    private DriveType driveType;

    @Column(nullable = false)
    private Transmission transmission;

    @Column(nullable = false)
    private EngineCapacity engineCapacity;

    @Column(nullable = false)
    private Integer power;

    @Column(nullable = false)
    private Double mileage;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
