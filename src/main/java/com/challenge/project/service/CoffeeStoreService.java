package com.challenge.project.service;

import com.challenge.project.domain.CoffeeEntity;
import com.challenge.project.domain.CoffeePriceHistoryEntity;
import com.challenge.project.domain.OrderEntity;
import com.challenge.project.model.AdditionalDto;
import com.challenge.project.model.CoffeeDto;
import com.challenge.project.model.CoffeePriceChangeResponse;
import com.challenge.project.model.OrderDto;
import com.challenge.project.model.exceptions.DataNotFoundException;
import com.challenge.project.repository.CoffeePriceHistoryRepository;
import com.challenge.project.repository.CoffeeRepository;
import com.challenge.project.repository.OrderRepository;
import com.challenge.project.util.CoffeeCombinator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CoffeeStoreService {
    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;
    private final CoffeePriceHistoryRepository coffeePriceHistoryRepository;

    public CoffeeDto saveCoffee(CoffeeDto coffee) {
        CoffeeEntity entity = new CoffeeEntity(coffee);
        return coffeeRepository.save(entity).toDto();
    }

    public List<CoffeeDto> showAllCoffees() {
        List<CoffeeEntity> lstCoffees = coffeeRepository.findAll();
        return lstCoffees.stream().map(CoffeeDto::new).collect(Collectors.toList());
    }

    public List<CoffeeDto> findCoffeeByNameOrDescription(String filter) {
        System.out.printf("FILTER: " + filter);
        List<CoffeeEntity> lstCoffees = coffeeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(filter, filter);
        System.out.printf("COFFEE COUNT: " + lstCoffees.size());
        return lstCoffees.stream().map(CoffeeDto::new).collect(Collectors.toList());
    }

    public OrderEntity saveOrder(OrderDto order) {
        /*List<CoffeeEntity> completeCoffees = new ArrayList<CoffeeEntity>();
        for(CoffeeDto coffee : order.getCoffees()){
            Optional<CoffeeEntity> coffeeById = coffeeRepository.findById(coffee.getId());
            coffeeById.ifPresent(completeCoffees::add);
        }*/
        Double total =  order.getCoffees().stream().map(CoffeeDto::getCost).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();
        Integer totalItems = order.getCoffees().size();

        order.setTotal(total);
        order.setTotalItems(totalItems);

        return orderRepository.save(new OrderEntity(order));
    }

    public List<OrderDto> showAllOrders() {
        List<OrderEntity> lstOrders = orderRepository.findAll();
        return lstOrders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public OrderDto findOrderById(Long id) {
        Optional<OrderEntity> result = orderRepository.findById(id);

        if(result.isPresent()) {
            return result.get().toDto();
        } else {
            throw new DataNotFoundException("Order");
        }
    }

    public List<OrderDto> findOrderByCoffeeNames(String coffeeName) {
        Optional<List<OrderEntity>> lstOrders = orderRepository.findByCoffeesNameContainingIgnoreCase(coffeeName);

        if(lstOrders.isPresent()) {
            return lstOrders.get().stream().map(OrderDto::new).collect(Collectors.toList());
        } else {
            throw new DataNotFoundException("Order");
        }
    }

    public List<OrderDto> findOrderByIdAndCoffeeNames(Long id, String coffeeName) {
        Optional<List<OrderEntity>> lstOrders = orderRepository.findByIdAndCoffeesNameContainingIgnoreCase(id, coffeeName);

        if(lstOrders.isPresent()) {
            return lstOrders.get().stream().map(OrderDto::new).collect(Collectors.toList());
        } else {
            throw new DataNotFoundException("Order");
        }
    }

    public CoffeeDto combineCoffeeAndAdditionals(CoffeeDto coffee, List<AdditionalDto> additionals) {
        CoffeeDto combinedCoffee = CoffeeCombinator.combineCoffeeWithExtras(coffee, additionals);
        return coffeeRepository.save(new CoffeeEntity(combinedCoffee)).toDto();
    }


    public CoffeePriceChangeResponse updateCoffeePrice(Long coffeeId, BigDecimal newPrice) {
        Optional<CoffeeEntity> coffee = coffeeRepository.findById(coffeeId);
        CoffeePriceChangeResponse response = new CoffeePriceChangeResponse();
        if(coffee.isPresent()) {
            coffee.get().setCost(newPrice);
            coffee = Optional.of(coffeeRepository.save(coffee.get()));
            CoffeePriceHistoryEntity coffePrice = new CoffeePriceHistoryEntity();
            coffePrice.setCoffee(coffee.get());
            coffePrice.setPrice(newPrice);
            coffePrice.setCreatedAt(LocalDateTime.now());
            coffeePriceHistoryRepository.save(coffePrice);
            List<CoffeePriceHistoryEntity> lstPrices = coffeePriceHistoryRepository.findByCoffee(coffee.get());
            response.setCoffee(coffee.get().toDto());
            response.setPrices(lstPrices);
        }

        return response;
    }


}
