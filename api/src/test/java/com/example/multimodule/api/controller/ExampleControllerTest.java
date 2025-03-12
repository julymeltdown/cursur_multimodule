package com.example.multimodule.api.controller;

import com.example.multimodule.api.dto.ExampleDto;
import com.example.multimodule.application.service.ExampleService;
import com.example.multimodule.domain.Example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExampleController.class)
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExampleService exampleService;

    @Test
    void findAll_ShouldReturnAllExamples() throws Exception {
        // given
        Example example1 = Example.builder()
                .id(1L)
                .name("Example 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now())
                .build();
        Example example2 = Example.builder()
                .id(2L)
                .name("Example 2")
                .description("Description 2")
                .createdAt(LocalDateTime.now())
                .build();
        List<Example> examples = Arrays.asList(example1, example2);

        when(exampleService.findAll()).thenReturn(examples);

        // when & then
        mockMvc.perform(get("/api/examples")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Example 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Example 2")));
    }

    @Test
    void findById_WhenExampleExists_ShouldReturnExample() throws Exception {
        // given
        Long id = 1L;
        Example example = Example.builder()
                .id(id)
                .name("Example 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now())
                .build();

        when(exampleService.findById(id)).thenReturn(Optional.of(example));

        // when & then
        mockMvc.perform(get("/api/examples/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Example 1")))
                .andExpect(jsonPath("$.description", is("Description 1")));
    }

    @Test
    void findById_WhenExampleDoesNotExist_ShouldReturnNotFound() throws Exception {
        // given
        Long id = 1L;

        when(exampleService.findById(id)).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(get("/api/examples/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_ShouldCreateExample() throws Exception {
        // given
        ExampleDto.Request request = new ExampleDto.Request();
        request.setName("New Example");
        request.setDescription("New Description");

        Example savedExample = Example.builder()
                .id(1L)
                .name("New Example")
                .description("New Description")
                .createdAt(LocalDateTime.now())
                .build();

        when(exampleService.save(any(Example.class))).thenReturn(savedExample);

        // when & then
        mockMvc.perform(post("/api/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Example")))
                .andExpect(jsonPath("$.description", is("New Description")));
    }

    @Test
    void update_WhenExampleExists_ShouldUpdateExample() throws Exception {
        // given
        Long id = 1L;
        ExampleDto.Request request = new ExampleDto.Request();
        request.setName("Updated Example");
        request.setDescription("Updated Description");

        Example existingExample = Example.builder()
                .id(id)
                .name("Example 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now())
                .build();

        Example updatedExample = Example.builder()
                .id(id)
                .name("Updated Example")
                .description("Updated Description")
                .createdAt(existingExample.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(exampleService.findById(id)).thenReturn(Optional.of(existingExample));
        when(exampleService.save(any(Example.class))).thenReturn(updatedExample);

        // when & then
        mockMvc.perform(put("/api/examples/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Example")))
                .andExpect(jsonPath("$.description", is("Updated Description")));
    }

    @Test
    void update_WhenExampleDoesNotExist_ShouldReturnNotFound() throws Exception {
        // given
        Long id = 1L;
        ExampleDto.Request request = new ExampleDto.Request();
        request.setName("Updated Example");
        request.setDescription("Updated Description");

        when(exampleService.findById(id)).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(put("/api/examples/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_WhenExampleExists_ShouldDeleteExample() throws Exception {
        // given
        Long id = 1L;
        Example example = Example.builder()
                .id(id)
                .name("Example 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now())
                .build();

        when(exampleService.findById(id)).thenReturn(Optional.of(example));
        doNothing().when(exampleService).deleteById(id);

        // when & then
        mockMvc.perform(delete("/api/examples/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_WhenExampleDoesNotExist_ShouldReturnNotFound() throws Exception {
        // given
        Long id = 1L;

        when(exampleService.findById(id)).thenReturn(Optional.empty());

        // when & then
        mockMvc.perform(delete("/api/examples/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
} 