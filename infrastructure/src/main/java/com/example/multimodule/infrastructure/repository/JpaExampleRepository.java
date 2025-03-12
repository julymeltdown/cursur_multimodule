package com.example.multimodule.infrastructure.repository;

import com.example.multimodule.domain.Example;
import com.example.multimodule.domain.repository.ExampleRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExampleRepository extends JpaRepository<Example, Long>, ExampleRepository {
    // JpaRepository에서 기본 메서드를 상속받으므로 추가 구현이 필요 없습니다.
} 