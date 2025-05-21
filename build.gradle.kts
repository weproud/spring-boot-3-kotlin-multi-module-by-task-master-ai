// Define versions in the root project
buildscript {
    repositories {
        mavenCentral()
    }
}

// Define versions for dependencies
object Versions {
    const val kotlin = "1.9.25"
    const val springBoot = "3.4.5"
    const val springDependencyManagement = "1.1.7"
    const val jacksonKotlin = "2.16.1"
    const val coroutines = "1.8.0"
}

plugins {
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
    kotlin("plugin.allopen") version "1.9.25" apply false
    kotlin("plugin.noarg") version "1.9.25" apply false
    kotlin("kapt") version "1.9.25" apply false
    id("org.springframework.boot") version "3.4.5" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

// Common properties for all projects
allprojects {
    group = "com.weproud"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

// Common configurations for all subprojects
subprojects {
    // Apply common plugins
    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
    }

    // Java version configuration
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    // Common Kotlin configuration
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            // Enable Kotlin's strict mode
            freeCompilerArgs = listOf(
                "-Xjsr305=strict",           // JSR-305 annotations for better null safety
                "-Xopt-in=kotlin.RequiresOptIn", // Enable opt-in annotations
                "-Xemit-jvm-type-annotations", // Enable JVM type annotations
                "-Xcontext-receivers"        // Enable context receivers (experimental)
            )
            jvmTarget = "21"                 // Target JVM version
            apiVersion = "1.9"               // Kotlin API version
            languageVersion = "1.9"          // Kotlin language version
        }
    }

    // Common test configuration
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    // Common dependencies for all subprojects
    dependencies {
        // Kotlin standard library and reflection
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // Kotlin Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.coroutines}")

        // Testing dependencies
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-engine")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.mockk:mockk:1.13.10")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
}

// Configure Spring Boot modules
configure(subprojects.filter { it.path.startsWith(":app:") }) {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.allopen")
    }

    // Spring Boot specific configurations
    springBoot {
        buildInfo()
    }

    // Configure all-open plugin for Spring
    allOpen {
        annotation("org.springframework.stereotype.Component")
        annotation("org.springframework.stereotype.Service")
        annotation("org.springframework.stereotype.Repository")
        annotation("org.springframework.stereotype.Controller")
        annotation("org.springframework.web.bind.annotation.RestController")
        annotation("org.springframework.boot.autoconfigure.SpringBootApplication")
    }

    dependencies {
        // Spring Boot dependencies
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-actuator")

        // Jackson for Kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonKotlin}")

        // Spring Boot DevTools
        developmentOnly("org.springframework.boot:spring-boot-devtools")

        // Spring Boot Configuration Processor
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        // Testing
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("org.springframework.security:spring-security-test")
    }
}

// Configure JPA modules
configure(subprojects.filter { it.path.startsWith(":domain:rds") }) {
    apply {
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("io.spring.dependency-management")
    }

    // Configure all-open plugin for JPA
    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

    // Configure no-arg plugin for JPA
    noArg {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

    dependencies {
        // JPA dependencies
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // Database drivers
        runtimeOnly("com.h2database:h2")
        runtimeOnly("org.postgresql:postgresql")

        // Flyway for database migrations
        implementation("org.flywaydb:flyway-core")
        implementation("org.flywaydb:flyway-postgresql")

        // QueryDSL for type-safe queries
        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
        annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    }
}

// Skip bootJar for non-application modules
configure(subprojects.filter { !it.path.startsWith(":app:") }) {
    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        enabled = false
    }

    tasks.withType<Jar> {
        enabled = true
    }
}
