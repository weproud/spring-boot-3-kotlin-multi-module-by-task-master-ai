package com.weproud.app.api.domain.service

import com.weproud.domain.common.dto.UserLoginRequest
import com.weproud.domain.common.dto.UserLoginResponse
import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.service.AuthenticationService
import com.weproud.domain.rds.repository.UserRepository
import com.weproud.framework.provider.jwt.JwtProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap

/**
 * 인증 서비스 구현 클래스
 */
@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : AuthenticationService {

    // 로그아웃된 토큰을 저장하는 맵 (실제 프로덕션에서는 Redis 등을 사용해야 함)
    private val blacklistedTokens = ConcurrentHashMap<String, Boolean>()

    /**
     * 사용자 로그인
     */
    @Transactional
    override fun login(request: UserLoginRequest): UserLoginResponse {
        // 사용자 조회
        val userEntity = userRepository.findByEmail(request.email)
            .orElseThrow { BadCredentialsException("잘못된 이메일 또는 비밀번호입니다") }

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.password, userEntity.password)) {
            throw BadCredentialsException("잘못된 이메일 또는 비밀번호입니다")
        }

        // 토큰 생성
        val accessToken = jwtProvider.createAccessToken(
            userEntity.id!!,
            userEntity.email,
            userEntity.role.name
        )
        val refreshToken = jwtProvider.createRefreshToken(userEntity.id)

        // 사용자 응답 생성
        val userResponse = UserResponse(
            id = userEntity.id,
            email = userEntity.email,
            name = userEntity.name,
            nickname = userEntity.nickname,
            phoneNumber = userEntity.phoneNumber,
            role = userEntity.role,
            status = userEntity.status,
            createdAt = userEntity.createdAt!!,
            updatedAt = userEntity.updatedAt!!
        )

        // 로그인 응답 반환
        return UserLoginResponse(
            token = accessToken,
            refreshToken = refreshToken,
            user = userResponse
        )
    }

    /**
     * 토큰 검증
     */
    override fun validateToken(token: String): Boolean {
        // 블랙리스트 확인
        if (blacklistedTokens.containsKey(token)) {
            return false
        }

        // 토큰 유효성 검증
        return jwtProvider.validateToken(token)
    }

    /**
     * 토큰에서 사용자 ID 추출
     */
    override fun getUserIdFromToken(token: String): Long {
        return jwtProvider.getUserId(token)
    }

    /**
     * 리프레시 토큰으로 새 액세스 토큰 발급
     */
    @Transactional
    override fun refreshToken(refreshToken: String): String {
        // 리프레시 토큰 유효성 검증
        if (!jwtProvider.validateToken(refreshToken)) {
            throw IllegalArgumentException("유효하지 않은 리프레시 토큰입니다")
        }

        // 사용자 ID 추출
        val userId = jwtProvider.getUserId(refreshToken)

        // 사용자 조회
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        // 새 액세스 토큰 생성
        return jwtProvider.createAccessToken(
            userEntity.id!!,
            userEntity.email,
            userEntity.role.name
        )
    }

    /**
     * 로그아웃
     */
    override fun logout(token: String) {
        // 토큰을 블랙리스트에 추가
        blacklistedTokens[token] = true
    }
}
