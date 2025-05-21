plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":core"))
    
    api("org.springframework.boot:spring-boot-starter-webflux")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }
