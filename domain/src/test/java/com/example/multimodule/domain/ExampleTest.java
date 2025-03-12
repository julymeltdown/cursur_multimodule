package com.example.multimodule.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExampleTest {

    @Test
    void testBuilder() {
        // given
        String name = "Test Example";
        String description = "This is a test example";
        LocalDateTime now = LocalDateTime.now();
        
        // when
        Example example = Example.builder()
                .name(name)
                .description(description)
                .createdAt(now)
                .build();
        
        // then
        assertNotNull(example);
        assertEquals(name, example.getName());
        assertEquals(description, example.getDescription());
        assertEquals(now, example.getCreatedAt());
    }
    
    @Test
    void testGetterAndSetter() {
        // given
        Example example = new Example();
        String name = "Test Example";
        String description = "This is a test example";
        LocalDateTime now = LocalDateTime.now();
        
        // when
        example.setName(name);
        example.setDescription(description);
        example.setCreatedAt(now);
        
        // then
        assertEquals(name, example.getName());
        assertEquals(description, example.getDescription());
        assertEquals(now, example.getCreatedAt());
    }
} 