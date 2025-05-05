package com.challenge.project.util;

import com.challenge.project.model.AdditionalDto;
import com.challenge.project.model.CoffeeDto;
import com.challenge.project.model.OrderDto;
import com.challenge.project.model.exceptions.EmptyParamException;
import org.hibernate.grammars.hql.HqlParser;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CoffeeCombinator {
    public static CoffeeDto combineCoffeeWithExtras(CoffeeDto coffee, List<AdditionalDto> additionals) {
        if(Objects.isNull(coffee)) {
            throw new EmptyParamException("Coffee");
        }

        if(additionals.isEmpty()) {
            throw new EmptyParamException("Additionals list");
        }

        CoffeeDto combinedCoffee = new CoffeeDto();

        String combinedName = additionals.stream()
                .sorted(
                        Comparator.comparing(AdditionalDto::getCost)
                                .reversed()
                                .thenComparing(AdditionalDto::getDescription))

                .map(AdditionalDto::getDescription)
                .collect(Collectors.joining(" ")) + " Coffee";

        BigDecimal newCost = additionals.stream()
                .map((a) -> additionals.size() == 1 ? a.getCost() : a.getCost().multiply(a.getCombineCostPercentage()).divide(BigDecimal.valueOf(100)) )
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(coffee.getCost());


        combinedCoffee.setName(combinedName);
        combinedCoffee.setDescription("Combined coffee");
        combinedCoffee.setCost(newCost);
        combinedCoffee.setEnabled(true);

        return combinedCoffee;
    }
}
