package com.weproud.domain.common.dto

import com.weproud.domain.common.model.UserRole
import com.weproud.domain.common.model.UserStatus
import java.time.ZonedDateTime

/**
 * 사용자 등록 요청 DTO
 */
data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String? = null,
    val phoneNumber: String? = null
)

/**
 * 사용자 로그인 요청 DTO
 */
data class UserLoginRequest(
    val email: String,
    val password: String
)

/**
 * 사용자 로그인 응답 DTO
 */
data class UserLoginResponse(
    val token: String,
    val refreshToken: String,
    val user: UserResponse
)

/**
 * 사용자 응답 DTO
 */
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String?,
    val phoneNumber: String?,
    val role: UserRole,
    val status: UserStatus,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)

/**
 * 사용자 프로필 업데이트 요청 DTO
 */
data class UserProfileUpdateRequest(
    val name: String? = null,
    val nickname: String? = null,
    val phoneNumber: String? = null
)

/**
 * 비밀번호 변경 요청 DTO
 */
data class PasswordChangeRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
