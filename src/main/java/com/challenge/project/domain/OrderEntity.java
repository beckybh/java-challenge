package com.challenge.project.domain;

import com.challenge.project.model.CoffeeDto;
import com.challenge.project.model.OrderDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    List<CoffeeEntity> coffees;

    @Column
    private Integer totalItems;

    @Column
    private Double total;

    @Column
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime orderDate;


    public OrderEntity(OrderDto dto) {
        this.id = dto.getId();
        this.description = dto.getDescription();
        this.coffees = dto.getCoffees().stream().map(CoffeeEntity::new).collect(Collectors.toList());
        this.totalItems = dto.getTotalItems();
        this.total = dto.getTotal();
        this.orderDate = dto.getOrderDate();
    }

    public OrderDto toDto() {
        return  OrderDto.builder()
                .id(id)
                .description(description)
                .coffees(coffees.stream().map(CoffeeDto::new).collect(Collectors.toList()))
                .totalItems(totalItems)
                .total(total)
                .orderDate(orderDate)
                .build();//new CoffeeDto(this);
    }
}
