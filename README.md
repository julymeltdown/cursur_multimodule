# 멀티모듈 스프링부트 프로젝트

레이어드 아키텍처를 기반으로 한 스프링부트 멀티모듈 프로젝트입니다.

## 프로젝트 구조

- `api`: 외부 API 인터페이스 (컨트롤러 레이어)
- `application`: 비즈니스 로직 (서비스 레이어)
- `domain`: 도메인 모델 및 비즈니스 규칙
- `infrastructure`: 데이터베이스 접근 및 외부 시스템 연동 (리포지토리 레이어)
- `common`: 공통 유틸리티 및 설정

## 기술 스택

- Spring Boot 3.2.3
- Spring Data JPA
- Flyway (데이터베이스 마이그레이션)
- H2 Database (개발 및 테스트용)
- PostgreSQL (프로덕션용)
- Lombok
- RestAssured (API 테스트)
- Spock (테스트 프레임워크)

## 애플리케이션 실행 방법

### 방법 1: 실행 스크립트 사용

Windows:
```
run.bat
```

Linux/Mac:
```
chmod +x run.sh
./run.sh
```

### 방법 2: Gradle 명령어 사용

```
./gradlew :api:bootRun
```

### 방법 3: IntelliJ IDEA에서 실행

1. IntelliJ IDEA에서 프로젝트를 엽니다.
2. 실행 구성 목록에서 'MultiModuleApplication'을 선택합니다.
3. 실행 버튼을 클릭합니다.

## API 엔드포인트

애플리케이션이 실행되면 다음 URL에서 API를 사용할 수 있습니다:

- `GET /api/examples`: 모든 예제 조회
- `GET /api/examples/{id}`: ID로 예제 조회
- `GET /api/examples/search?name={name}`: 이름으로 예제 검색
- `POST /api/examples`: 새 예제 생성
- `PUT /api/examples/{id}`: 예제 업데이트
- `DELETE /api/examples/{id}`: 예제 삭제

## H2 콘솔

개발 환경에서는 H2 콘솔을 통해 데이터베이스를 확인할 수 있습니다:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- 사용자명: `sa`
- 비밀번호: `password` 