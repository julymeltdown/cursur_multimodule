package com.example.multimodule.application.service

import com.example.multimodule.domain.Example
import com.example.multimodule.infrastructure.repository.ExampleRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class ExampleServiceSpec extends Specification {

    ExampleRepository exampleRepository = Mock()
    
    @Subject
    ExampleService exampleService = new ExampleService(exampleRepository)
    
    def "findAll should return all examples"() {
        given:
        def examples = [
            Example.builder().id(1L).name("Example 1").build(),
            Example.builder().id(2L).name("Example 2").build()
        ]
        
        when:
        def result = exampleService.findAll()
        
        then:
        1 * exampleRepository.findAll() >> examples
        result == examples
        result.size() == 2
    }
    
    def "findById should return example when exists"() {
        given:
        def id = 1L
        def example = Example.builder().id(id).name("Example 1").build()
        
        when:
        def result = exampleService.findById(id)
        
        then:
        1 * exampleRepository.findById(id) >> Optional.of(example)
        result.isPresent()
        result.get() == example
    }
    
    def "findById should return empty when not exists"() {
        given:
        def id = 1L
        
        when:
        def result = exampleService.findById(id)
        
        then:
        1 * exampleRepository.findById(id) >> Optional.empty()
        !result.isPresent()
    }
    
    def "findByNameContaining should return matching examples"() {
        given:
        def name = "Example"
        def examples = [
            Example.builder().id(1L).name("Example 1").build(),
            Example.builder().id(2L).name("Example 2").build()
        ]
        
        when:
        def result = exampleService.findByNameContaining(name)
        
        then:
        1 * exampleRepository.findByNameContaining(name) >> examples
        result == examples
        result.size() == 2
    }
    
    def "save should set createdAt for new example"() {
        given:
        def example = Example.builder().name("New Example").build()
        
        when:
        exampleService.save(example)
        
        then:
        1 * exampleRepository.save(_) >> { Example e ->
            assert e.createdAt != null
            assert e.updatedAt == null
            return e
        }
    }
    
    def "save should set updatedAt for existing example"() {
        given:
        def example = Example.builder()
                .id(1L)
                .name("Existing Example")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build()
        
        when:
        exampleService.save(example)
        
        then:
        1 * exampleRepository.save(_) >> { Example e ->
            assert e.createdAt != null
            assert e.updatedAt != null
            return e
        }
    }
    
    def "deleteById should call repository deleteById"() {
        given:
        def id = 1L
        
        when:
        exampleService.deleteById(id)
        
        then:
        1 * exampleRepository.deleteById(id)
    }
} 