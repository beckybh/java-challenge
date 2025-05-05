package com.challenge.project.domain;

import com.challenge.project.model.AdditionalDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "additionals")
public class AdditionalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private BigDecimal cost;

    @Column(name = "combine_cost_percentage")
    private BigDecimal combineCostPercentage;

    public AdditionalDto toDto() {
        return AdditionalDto.builder()
                .id(id)
                .description(description)
                .cost(cost)
                .combineCostPercentage(combineCostPercentage)
                .build();
    }
}
