package com.example.multimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.multimodule")
@EntityScan(basePackages = "com.example.multimodule.domain")
@EnableJpaRepositories(basePackages = "com.example.multimodule.infrastructure.repository")
public class MultiModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiModuleApplication.class, args);
    }
} 