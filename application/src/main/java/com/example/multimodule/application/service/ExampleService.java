package com.example.multimodule.application.service;

import com.example.multimodule.domain.Example;
import com.example.multimodule.domain.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    @Transactional(readOnly = true)
    public List<Example> findAll() {
        return exampleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Example> findById(Long id) {
        return exampleRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Example> findByNameContaining(String name) {
        return exampleRepository.findByNameContaining(name);
    }

    @Transactional
    public Example save(Example example) {
        if (example.getId() == null) {
            example.setCreatedAt(LocalDateTime.now());
        } else {
            example.setUpdatedAt(LocalDateTime.now());
        }
        return exampleRepository.save(example);
    }

    @Transactional
    public void deleteById(Long id) {
        exampleRepository.deleteById(id);
    }
} 