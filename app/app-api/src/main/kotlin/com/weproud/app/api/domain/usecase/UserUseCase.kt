package com.weproud.app.api.domain.usecase

import com.weproud.app.api.domain.model.User

/**
 * 사용자 등록 유스케이스
 */
interface RegisterUserUseCase {
    /**
     * 사용자 등록 실행
     */
    fun execute(email: String, password: String, name: String): User
}

/**
 * 사용자 로그인 유스케이스
 */
interface LoginUserUseCase {
    /**
     * 로그인 실행
     */
    fun execute(email: String, password: String): LoginResult
    
    /**
     * 로그인 결과
     */
    data class LoginResult(
        val user: User,
        val token: String
    )
}

/**
 * 사용자 정보 조회 유스케이스
 */
interface GetUserUseCase {
    /**
     * ID로 사용자 조회
     */
    fun execute(id: Long): User?
    
    /**
     * 이메일로 사용자 조회
     */
    fun executeByEmail(email: String): User?
}

/**
 * 사용자 정보 수정 유스케이스
 */
interface UpdateUserUseCase {
    /**
     * 사용자 정보 수정
     */
    fun execute(id: Long, name: String?, password: String?): User
}
