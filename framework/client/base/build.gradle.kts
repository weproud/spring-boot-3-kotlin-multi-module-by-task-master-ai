dependencies {
    // Core module dependency
    implementation(project(":core"))
    
    // WebClient dependencies
    implementation("org.springframework:spring-webflux")
    implementation("io.projectreactor.netty:reactor-netty-http")
}
