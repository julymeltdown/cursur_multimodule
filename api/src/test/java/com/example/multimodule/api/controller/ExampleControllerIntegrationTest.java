package com.example.multimodule.api.controller;

import com.example.multimodule.api.dto.ExampleDto;
import com.example.multimodule.domain.Example;
import com.example.multimodule.infrastructure.repository.ExampleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExampleControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ExampleRepository exampleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        exampleRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveExample() {
        // Create example
        ExampleDto.Request request = new ExampleDto.Request();
        request.setName("Integration Test Example");
        request.setDescription("Created during integration test");

        // Create and verify
        Long id = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/examples")
                .then()
                .statusCode(201)
                .body("name", equalTo("Integration Test Example"))
                .body("description", equalTo("Created during integration test"))
                .extract()
                .path("id");

        // Retrieve and verify
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/examples/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()))
                .body("name", equalTo("Integration Test Example"))
                .body("description", equalTo("Created during integration test"));
    }

    @Test
    void shouldReturnAllExamples() {
        // Create test data
        Example example1 = Example.builder()
                .name("Example 1")
                .description("Description 1")
                .createdAt(LocalDateTime.now())
                .build();
        Example example2 = Example.builder()
                .name("Example 2")
                .description("Description 2")
                .createdAt(LocalDateTime.now())
                .build();

        exampleRepository.save(example1);
        exampleRepository.save(example2);

        // Retrieve and verify
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/examples")
                .then()
                .statusCode(200)
                .body("", hasSize(2))
                .body("name", hasItems("Example 1", "Example 2"));
    }

    @Test
    void shouldUpdateExample() {
        // Create test data
        Example example = Example.builder()
                .name("Original Name")
                .description("Original Description")
                .createdAt(LocalDateTime.now())
                .build();
        Example savedExample = exampleRepository.save(example);

        // Update request
        ExampleDto.Request updateRequest = new ExampleDto.Request();
        updateRequest.setName("Updated Name");
        updateRequest.setDescription("Updated Description");

        // Update and verify
        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/api/examples/{id}", savedExample.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("description", equalTo("Updated Description"));

        // Verify the update was persisted
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/examples/{id}", savedExample.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("description", equalTo("Updated Description"));
    }

    @Test
    void shouldDeleteExample() {
        // Create test data
        Example example = Example.builder()
                .name("Example to Delete")
                .description("This example will be deleted")
                .createdAt(LocalDateTime.now())
                .build();
        Example savedExample = exampleRepository.save(example);

        // Delete
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/examples/{id}", savedExample.getId())
                .then()
                .statusCode(204);

        // Verify it's gone
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/examples/{id}", savedExample.getId())
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnNotFoundForNonExistentExample() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/examples/999")
                .then()
                .statusCode(404);
    }
} 