package com.challenge.project.repository;

import com.challenge.project.domain.CoffeeEntity;
import com.challenge.project.model.CoffeeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {
    public CoffeeEntity save(CoffeeEntity coffee);
    public Optional<CoffeeEntity> findById(Long id);
    public List<CoffeeEntity> findAll();
    public List<CoffeeEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
