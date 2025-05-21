package com.weproud.app.api.domain.model

import java.time.LocalDateTime

/**
 * 사용자 도메인 모델
 */
data class User(
    val id: Long? = null,
    val email: String,
    val name: String,
    val password: String,
    val roles: List<String> = listOf("USER"),
    val status: UserStatus = UserStatus.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

/**
 * 사용자 상태 열거형
 */
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    DELETED
}
