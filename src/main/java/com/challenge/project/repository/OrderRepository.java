package com.challenge.project.repository;

import com.challenge.project.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    public OrderEntity save(OrderEntity order);
    public List<OrderEntity> findAll();
    public Optional<OrderEntity> findById(Long id);
    public Optional<List<OrderEntity>> findByCoffeesNameContainingIgnoreCase(String coffeeName);
    public Optional<List<OrderEntity>> findByIdAndCoffeesNameContainingIgnoreCase(Long id, String coffeeName);
}
