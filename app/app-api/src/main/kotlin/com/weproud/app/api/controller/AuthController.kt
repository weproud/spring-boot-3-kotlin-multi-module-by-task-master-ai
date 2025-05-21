package com.weproud.app.api.controller

import com.weproud.app.api.controller.dto.LoginRequest
import com.weproud.app.api.controller.dto.LoginResponse
import com.weproud.app.api.controller.dto.RegisterRequest
import com.weproud.app.api.service.AuthService
import com.weproud.core.config.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 인증 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    
    /**
     * 사용자 등록
     */
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ApiResponse<Unit> {
        authService.register(request)
        return ApiResponse.success()
    }
    
    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ApiResponse<LoginResponse> {
        val response = authService.login(request)
        return ApiResponse.success(response)
    }
    
    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    fun logout(): ApiResponse<Unit> {
        authService.logout()
        return ApiResponse.success()
    }
}
