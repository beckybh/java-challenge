package com.challenge.project.model;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdditionalDto {
    private Long id;
    private String description;
    private BigDecimal cost;
    private BigDecimal combineCostPercentage;
}
