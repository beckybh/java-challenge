package com.challenge.project.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CombineCoffeeRequest {
    private CoffeeDto coffee;
    private List<AdditionalDto> additionals;
}
