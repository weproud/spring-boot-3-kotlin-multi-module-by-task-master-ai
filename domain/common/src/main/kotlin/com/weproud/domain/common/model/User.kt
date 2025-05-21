package com.weproud.domain.common.model

import java.time.ZonedDateTime

/**
 * 사용자 도메인 모델
 */
data class User(
    val id: Long? = null,
    val email: String,
    val password: String,
    val name: String,
    val nickname: String? = null,
    val phoneNumber: String? = null,
    val role: UserRole,
    val status: UserStatus,
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
)

/**
 * 사용자 역할 열거형
 */
enum class UserRole {
    USER,
    ADMIN,
    TEACHER
}

/**
 * 사용자 상태 열거형
 */
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED
}
