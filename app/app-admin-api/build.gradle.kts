dependencies {
    // Core module dependency
    implementation(project(":core"))
    
    // Domain modules
    implementation(project(":domain:common"))
    implementation(project(":domain:rds"))
    
    // Framework modules
    implementation(project(":framework:client:base"))
    implementation(project(":framework:provider:jwt"))
    implementation(project(":framework:redis"))
    
    // Spring Boot Starter dependencies
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
}
