# Spring Boot 3 Kotlin 멀티 모듈 프로젝트

# 중단: 가성비가 안나옴

task-master ai를 이용해서 spring boot kotlin multi module을 구현하려고 하였으나 실패.

PRD를 정말 세세하게 나누어서 작성하면 가능할 것 같은데, 시간이 너무 오래걸림.

내가 원하는 작업만 task를 만드는게 쉽지 않음.

원하는 멀티모듈 구조를 만들어 놓고 그 이후에 디테일한 작업을 해야하는게 효율적일듯.

PRD만 잘 작성해도 task-master ai를 이용해서 바이브 코딩이 가능할 것 같다.

---

이 프로젝트는 Spring Boot 3와 Kotlin을 사용한 멀티 모듈 구조의 웹 애플리케이션입니다.

## 프로젝트 구조

```
.
├── app
│   ├── app-api                # 사용자 API 애플리케이션
│   ├── app-admin-api          # 관리자 API 애플리케이션
├── core                       # 공통 코어 모듈
├── docs                       # API 문서화 모듈
├── domain
│   ├── common                 # 도메인 공통 모듈
│   └── rds                    # 엔티티 및 레포지토리 모듈
├── framework
│   ├── client                 # HTTP 클라이언트 모듈
│   │   ├── ai                 # AI API 클라이언트
│   │   ├── base               # WebClient 공통 설정
│   │   └── kakao              # 카카오 API 클라이언트
│   ├── provider
│   │   └── jwt                # JWT 제공자 모듈
│   └── redis                  # Redis 모듈
└── http                       # HTTP 요청 파일
    ├── admin                  # 관리자 API 요청
    ├── teacher                # 강사 API 요청
    └── user                   # 사용자 API 요청
```

## 모듈 설명

### App 모듈

- **app-api**: 사용자 API 애플리케이션
- **app-admin-api**: 관리자 API 애플리케이션

### Core 모듈

공통 유틸리티, 예외 처리, 기본 모델 등 프로젝트 전반에서 사용되는 코어 기능을 제공합니다.

### Domain 모듈

- **common**: 도메인 공통 모델, DTO, 상수 등을 포함
- **rds**: JPA 엔티티 및 레포지토리를 포함

### Framework 모듈

- **client**: 외부 API 클라이언트
  - **ai**: AI API 클라이언트
  - **base**: WebClient 공통 설정
  - **kakao**: 카카오 API 클라이언트
- **provider**:
  - **jwt**: JWT 토큰 생성 및 검증
- **redis**: Redis 캐싱 및 세션 관리

### HTTP 모듈

API 테스트를 위한 HTTP 요청 파일을 포함합니다.

### Docs 모듈

API 문서화를 위한 Spring REST Docs 설정 및 문서 파일을 포함합니다.

## 빌드 및 실행

### 빌드

```bash
./gradlew clean build
```

### 사용자 API 실행

```bash
./gradlew :app:app-api:bootRun
```

### 관리자 API 실행

```bash
./gradlew :app:app-admin-api:bootRun
```

## API 문서

API 문서는 다음 URL에서 확인할 수 있습니다:

- 사용자 API: http://localhost:8080/docs/index.html
- 관리자 API: http://localhost:8081/docs/index.html
