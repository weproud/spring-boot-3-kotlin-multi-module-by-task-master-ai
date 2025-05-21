package com.weproud.app.admin.controller.dto

/**
 * 관리자 로그인 요청 DTO
 */
data class AdminLoginRequest(
    val email: String,
    val password: String
)

/**
 * 관리자 로그인 응답 DTO
 */
data class AdminLoginResponse(
    val token: String,
    val adminId: Long,
    val name: String,
    val email: String,
    val roles: List<String>
)
