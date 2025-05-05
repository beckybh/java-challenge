package com.challenge.project.model;

import com.challenge.project.domain.CoffeePriceHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoffeePriceChangeResponse {
    private CoffeeDto coffee;
    private List<CoffeePriceHistoryEntity> prices;
}
