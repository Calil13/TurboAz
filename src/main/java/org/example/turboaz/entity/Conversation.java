package org.example.turboaz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Users buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users seller;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @CreationTimestamp
    private LocalDateTime createdAt;
}