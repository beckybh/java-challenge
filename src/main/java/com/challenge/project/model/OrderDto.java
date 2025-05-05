package com.challenge.project.model;

import com.challenge.project.domain.CoffeeEntity;
import com.challenge.project.domain.OrderEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;
    private String description;
    List<CoffeeDto> coffees;
    private Integer totalItems;
    private Double total;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime orderDate;

    public OrderDto() {
        this.coffees = new ArrayList<>();
    }

    public OrderDto(OrderEntity entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.coffees = entity.getCoffees().stream().map(CoffeeDto::new).collect(Collectors.toList());
        this.totalItems = entity.getTotalItems();
        this.total = entity.getTotal();
        this.orderDate = entity.getOrderDate();

    }
}
