-- 초기 데이터베이스 스키마 생성

-- 예시 테이블 생성
CREATE TABLE IF NOT EXISTS example (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- 인덱스 생성
CREATE INDEX IF NOT EXISTS idx_example_name ON example(name);

-- 주석
COMMENT ON TABLE example IS '예시 테이블';
COMMENT ON COLUMN example.id IS '고유 식별자';
COMMENT ON COLUMN example.name IS '이름';
COMMENT ON COLUMN example.description IS '설명';
COMMENT ON COLUMN example.created_at IS '생성 시간';
COMMENT ON COLUMN example.updated_at IS '수정 시간'; 