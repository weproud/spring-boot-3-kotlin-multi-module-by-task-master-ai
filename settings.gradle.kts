rootProject.name = "hello-spring-boot-3-kotlin-by-vibe-coding"

include(
    "app:app-api",
    "app:app-batch",
    "core",
    "domain:rds",
    "framework:client:base",
    "framework:client:build",
    "framework:client:kakao",
    "framework:provider:auth",
    "framework:provider:jwt",
    "framework:redis"
)
