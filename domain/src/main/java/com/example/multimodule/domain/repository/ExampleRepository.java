package com.example.multimodule.domain.repository;

import com.example.multimodule.domain.Example;

import java.util.List;
import java.util.Optional;

public interface ExampleRepository {
    
    List<Example> findAll();
    
    Optional<Example> findById(Long id);
    
    List<Example> findByNameContaining(String name);
    
    boolean existsByName(String name);
    
    Example save(Example example);
    
    void deleteById(Long id);
} 