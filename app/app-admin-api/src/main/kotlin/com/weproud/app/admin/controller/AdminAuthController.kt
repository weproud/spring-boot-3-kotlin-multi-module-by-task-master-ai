package com.weproud.app.admin.controller

import com.weproud.app.admin.controller.dto.AdminLoginRequest
import com.weproud.app.admin.controller.dto.AdminLoginResponse
import com.weproud.app.admin.service.AdminAuthService
import com.weproud.core.config.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 관리자 인증 컨트롤러
 */
@RestController
@RequestMapping("/admin-api/auth")
class AdminAuthController(
    private val adminAuthService: AdminAuthService
) {
    
    /**
     * 관리자 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody request: AdminLoginRequest): ApiResponse<AdminLoginResponse> {
        val response = adminAuthService.login(request)
        return ApiResponse.success(response)
    }
    
    /**
     * 관리자 로그아웃
     */
    @PostMapping("/logout")
    fun logout(): ApiResponse<Unit> {
        adminAuthService.logout()
        return ApiResponse.success()
    }
}
