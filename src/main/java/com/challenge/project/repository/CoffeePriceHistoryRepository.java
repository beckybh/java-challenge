package com.challenge.project.repository;

import com.challenge.project.domain.CoffeeEntity;
import com.challenge.project.domain.CoffeePriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeePriceHistoryRepository extends JpaRepository<CoffeePriceHistoryEntity, Long> {
    public CoffeePriceHistoryEntity save(CoffeePriceHistoryEntity entity);
    public List<CoffeePriceHistoryEntity> findByCoffee(CoffeeEntity coffee);
}
