package com.challenge.project.model;


import com.challenge.project.domain.CoffeeEntity;
import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CoffeeDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private boolean enabled;

    public CoffeeDto(CoffeeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.cost = entity.getCost();
        this.enabled = entity.isEnabled();
    }
}
