package com.challenge.project.controller;

import com.challenge.project.model.CoffeeDto;
import com.challenge.project.model.CoffeePriceChangeResponse;
import com.challenge.project.model.CombineCoffeeRequest;
import com.challenge.project.model.OrderDto;
import com.challenge.project.model.exceptions.DataCreationException;
import com.challenge.project.model.exceptions.DataNotFoundException;
import com.challenge.project.service.CoffeeStoreService;
import com.challenge.project.util.CoffeeCombinator;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CoffeeStoreController {
    public final CoffeeStoreService coffeeStoreService;

    @PostMapping("/coffee")
    public ResponseEntity<CoffeeDto> saveCoffee(@RequestBody CoffeeDto coffee) {
        CoffeeDto result;

        try {
            result = coffeeStoreService.saveCoffee(coffee);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);

        } catch (Exception ex) {
            throw new DataCreationException("Coffee");
        }

    }

    @GetMapping("/coffees")
    public ResponseEntity<List<CoffeeDto>> getAllCoffees() {
        List<CoffeeDto> lstResult = coffeeStoreService.showAllCoffees();

        if(!lstResult.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lstResult);
        } else {
            throw new DataNotFoundException("Coffee");
        }
    }

    @GetMapping("/coffee")
    public ResponseEntity<List<CoffeeDto>> findByNameOrDescription(@RequestParam(name = "filter") String filter) {
        List<CoffeeDto> lstResult = coffeeStoreService.findCoffeeByNameOrDescription(filter);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lstResult);
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDto> saveCoffee(@RequestBody OrderDto order) {
        OrderDto result;

        try {
            result = coffeeStoreService.saveOrder(order).toDto();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);

        } catch (Exception ex) {
            System.out.println("ERROR:: " + ex.getMessage());
            throw new DataCreationException("Order");
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> lstResult = coffeeStoreService.showAllOrders();

        if(!lstResult.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lstResult);
        } else {
            throw new DataNotFoundException("Order");
        }
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable(name = "id") Long id) {
        OrderDto result = coffeeStoreService.findOrderById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> findOrderByCoffeNames(@RequestParam(name = "coffeeName") String coffeeName) {
        List<OrderDto> lstResult = coffeeStoreService.findOrderByCoffeeNames(coffeeName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lstResult);
    }

    @GetMapping("/order/{id}/coffee")
    public ResponseEntity<List<OrderDto>> findOrderByIdAndCoffeeNames(@PathVariable(name = "id") Long id, @RequestParam(name = "coffeeName") String coffeeName) {
        List<OrderDto> lstResult = coffeeStoreService.findOrderByIdAndCoffeeNames(id, coffeeName);

        if(!lstResult.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lstResult);
        } else {
            throw new DataNotFoundException("Order");
        }
    }

    @PostMapping("/coffee/combine")
    public ResponseEntity<CoffeeDto> getCombinedCoffee(@RequestBody CombineCoffeeRequest request) {

        CoffeeDto result;

        try {
            result = coffeeStoreService.combineCoffeeAndAdditionals(request.getCoffee(), request.getAdditionals());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);

        } catch (Exception ex) {
            throw new DataCreationException("Coffee");
        }
    }

    @PatchMapping("/coffee/price")
    public ResponseEntity<CoffeePriceChangeResponse> changeCoffeePrice(@RequestParam(name = "coffeeId") Long coffeeId, @RequestParam(name = "newPrice")BigDecimal newPrice) {

        CoffeePriceChangeResponse result;

        try {
            result = coffeeStoreService.updateCoffeePrice(coffeeId, newPrice);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);

        } catch (Exception ex) {
            throw new DataCreationException("Coffee");
        }
    }

}
