package com.example.multimodule.api.controller;

import com.example.multimodule.api.dto.ExampleDto;
import com.example.multimodule.application.service.ExampleService;
import com.example.multimodule.domain.Example;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examples")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping
    public ResponseEntity<List<ExampleDto.Response>> findAll() {
        List<ExampleDto.Response> responses = exampleService.findAll().stream()
                .map(ExampleDto.Response::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExampleDto.Response> findById(@PathVariable Long id) {
        return exampleService.findById(id)
                .map(ExampleDto.Response::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExampleDto.Response>> findByNameContaining(@RequestParam String name) {
        List<ExampleDto.Response> responses = exampleService.findByNameContaining(name).stream()
                .map(ExampleDto.Response::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ExampleDto.Response> create(@Valid @RequestBody ExampleDto.Request request) {
        Example example = request.toEntity();
        Example savedExample = exampleService.save(example);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExampleDto.Response.fromEntity(savedExample));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExampleDto.Response> update(@PathVariable Long id, @Valid @RequestBody ExampleDto.Request request) {
        return exampleService.findById(id)
                .map(example -> {
                    example.setName(request.getName());
                    example.setDescription(request.getDescription());
                    return exampleService.save(example);
                })
                .map(ExampleDto.Response::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (exampleService.findById(id).isPresent()) {
            exampleService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 