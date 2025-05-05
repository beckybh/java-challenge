package com.challenge.project.domain;

import com.challenge.project.model.CoffeeDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "coffees")
public class CoffeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 200)
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal cost;

    @Column
    private boolean enabled;

    public CoffeeDto toDto() {
        return  CoffeeDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .cost(cost)
                .enabled(enabled)
                .build();//new CoffeeDto(this);
    }

    public CoffeeEntity(CoffeeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.cost = dto.getCost();
        this.enabled = dto.isEnabled();
    }

}
