rootProject.name = "hello-spring-boot-3-kotlin-by-vibe-coding"

// App modules
include(":app:app-api")
include(":app:app-admin-api")

// Core module
include(":core")

// Domain modules
include(":domain:common")
include(":domain:rds")

// Framework modules
include(":framework:client:ai")
include(":framework:client:base")
include(":framework:client:kakao")
include(":framework:provider:jwt")
include(":framework:redis")

// HTTP modules
include(":http:admin")
include(":http:teacher")
include(":http:user")

// Docs module
include(":docs")
