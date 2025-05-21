package com.weproud.app.admin.service

import com.weproud.app.admin.controller.dto.AdminLoginRequest
import com.weproud.app.admin.controller.dto.AdminLoginResponse
import com.weproud.core.exception.BusinessException
import com.weproud.framework.provider.jwt.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * 관리자 인증 서비스
 */
@Service
class AdminAuthService(
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder
) {
    
    /**
     * 관리자 로그인
     */
    fun login(request: AdminLoginRequest): AdminLoginResponse {
        // TODO: 실제 관리자 인증 로직 구현
        // 임시 구현: 테스트용 관리자 정보 반환
        if (request.email == "admin@example.com" && request.password == "admin123") {
            val adminId = 1L
            val roles = listOf("ADMIN")
            val token = jwtProvider.generateToken(adminId, roles)
            
            return AdminLoginResponse(
                token = token,
                adminId = adminId,
                name = "관리자",
                email = request.email,
                roles = roles
            )
        } else {
            throw BusinessException("AUTH_ERROR", "이메일 또는 비밀번호가 일치하지 않습니다.")
        }
    }
    
    /**
     * 관리자 로그아웃
     */
    fun logout() {
        // TODO: 로그아웃 로직 구현
        // 1. 토큰 블랙리스트에 추가
    }
}
