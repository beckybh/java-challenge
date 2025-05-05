package com.challenge.project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "coffee_price_history")
public class CoffeePriceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CoffeeEntity coffee;

    @Column
    private BigDecimal price;

    @Column
    private LocalDateTime createdAt;
}
