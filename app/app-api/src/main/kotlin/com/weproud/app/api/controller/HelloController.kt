package com.weproud.app.api.controller

import com.weproud.core.config.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 간단한 Hello World 컨트롤러
 */
@RestController
@RequestMapping("/api/hello")
class HelloController {
    
    /**
     * Hello World 메시지 반환
     */
    @GetMapping
    fun hello(): ApiResponse<Map<String, String>> {
        val response = mapOf(
            "message" to "Hello, Spring Boot 3 with Kotlin!",
            "version" to "1.0.0",
            "framework" to "Spring Boot 3.4.5",
            "language" to "Kotlin 1.9.25"
        )
        
        return ApiResponse.success(response)
    }
}
