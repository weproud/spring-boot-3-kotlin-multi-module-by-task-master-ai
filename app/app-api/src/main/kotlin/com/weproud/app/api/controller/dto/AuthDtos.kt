package com.weproud.app.api.controller.dto

/**
 * 사용자 등록 요청 DTO
 */
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

/**
 * 로그인 요청 DTO
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * 로그인 응답 DTO
 */
data class LoginResponse(
    val token: String,
    val userId: Long,
    val name: String,
    val email: String,
    val roles: List<String>
)
