plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":framework:provider:jwt"))
    
    api("org.springframework.boot:spring-boot-starter-security")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }
