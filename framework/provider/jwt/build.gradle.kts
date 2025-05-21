dependencies {
    // Core module dependency
    implementation(project(":core"))
    
    // JWT dependencies
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    
    // Spring Security
    implementation("org.springframework.security:spring-security-core")
}
