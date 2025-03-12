package com.example.multimodule.infrastructure.repository;

import com.example.multimodule.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {
    
    List<Example> findByNameContaining(String name);
    
    boolean existsByName(String name);
} 