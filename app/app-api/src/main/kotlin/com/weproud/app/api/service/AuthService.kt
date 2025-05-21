package com.weproud.app.api.service

import com.weproud.app.api.controller.dto.LoginRequest
import com.weproud.app.api.controller.dto.LoginResponse
import com.weproud.app.api.controller.dto.RegisterRequest
import com.weproud.core.exception.BusinessException
import com.weproud.framework.provider.jwt.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * 인증 서비스
 */
@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder
) {
    
    /**
     * 사용자 등록
     */
    fun register(request: RegisterRequest) {
        // TODO: 사용자 등록 로직 구현
        // 1. 이메일 중복 확인
        // 2. 비밀번호 암호화
        // 3. 사용자 정보 저장
    }
    
    /**
     * 로그인
     */
    fun login(request: LoginRequest): LoginResponse {
        // TODO: 실제 사용자 인증 로직 구현
        // 임시 구현: 테스트용 사용자 정보 반환
        if (request.email == "user@example.com" && request.password == "password123") {
            val userId = 1L
            val roles = listOf("USER")
            val token = jwtProvider.generateToken(userId, roles)
            
            return LoginResponse(
                token = token,
                userId = userId,
                name = "홍길동",
                email = request.email,
                roles = roles
            )
        } else {
            throw BusinessException("AUTH_ERROR", "이메일 또는 비밀번호가 일치하지 않습니다.")
        }
    }
    
    /**
     * 로그아웃
     */
    fun logout() {
        // TODO: 로그아웃 로직 구현
        // 1. 토큰 블랙리스트에 추가
    }
}
