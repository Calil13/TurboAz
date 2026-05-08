package org.example.turboaz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id")
    private Car car;

    public Images(String imageUrl, Car car) {
        this.imageUrl = imageUrl;
        this.car = car;
    }
}
