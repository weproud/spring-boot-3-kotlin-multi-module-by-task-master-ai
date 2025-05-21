package com.weproud.domain.common.service

import com.weproud.domain.common.dto.UserLoginRequest
import com.weproud.domain.common.dto.UserLoginResponse

/**
 * 인증 서비스 인터페이스
 */
interface AuthenticationService {
    /**
     * 사용자 로그인
     *
     * @param request 로그인 요청 정보
     * @return 로그인 응답 정보 (토큰, 사용자 정보 등)
     */
    fun login(request: UserLoginRequest): UserLoginResponse

    /**
     * 토큰 검증
     *
     * @param token 검증할 토큰
     * @return 토큰 유효성 여부
     */
    fun validateToken(token: String): Boolean

    /**
     * 토큰에서 사용자 ID 추출
     *
     * @param token 토큰
     * @return 사용자 ID
     */
    fun getUserIdFromToken(token: String): Long

    /**
     * 리프레시 토큰으로 새 액세스 토큰 발급
     *
     * @param refreshToken 리프레시 토큰
     * @return 새 액세스 토큰
     */
    fun refreshToken(refreshToken: String): String

    /**
     * 로그아웃
     *
     * @param token 무효화할 토큰
     */
    fun logout(token: String)
}
