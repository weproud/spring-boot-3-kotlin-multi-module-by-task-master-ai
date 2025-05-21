# Domain 모듈

이 모듈은 도메인 모델과 관련된 기능을 제공합니다.

## 하위 모듈

### common

도메인 공통 모델, DTO, 상수 등을 포함합니다.

#### 주요 기능

- 공통 상수 (Status 등)
- 기본 모델 인터페이스 (BaseModel)
- 페이지네이션 DTO (PageRequest, PageResponse)

### rds

JPA 엔티티 및 레포지토리를 포함합니다.

#### 주요 기능

- JPA 설정 (JpaConfig)
- 기본 엔티티 클래스 (BaseEntity)
- 레포지토리 인터페이스
