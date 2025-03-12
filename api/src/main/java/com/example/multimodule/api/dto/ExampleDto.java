package com.example.multimodule.api.dto;

import com.example.multimodule.domain.Example;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ExampleDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "이름은 필수입니다.")
        @Size(min = 1, max = 255, message = "이름은 1자 이상 255자 이하여야 합니다.")
        private String name;
        
        private String description;
        
        public Example toEntity() {
            return Example.builder()
                    .name(name)
                    .description(description)
                    .build();
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public static Response fromEntity(Example example) {
            return Response.builder()
                    .id(example.getId())
                    .name(example.getName())
                    .description(example.getDescription())
                    .createdAt(example.getCreatedAt())
                    .updatedAt(example.getUpdatedAt())
                    .build();
        }
    }
} 