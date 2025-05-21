package com.weproud.app.admin.controller.dto

import com.weproud.domain.common.constant.Status
import java.time.LocalDateTime

/**
 * 사용자 상세 응답 DTO
 */
data class UserDetailResponse(
    val id: Long,
    val email: String,
    val name: String,
    val status: Status,
    val roles: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

/**
 * 사용자 상태 업데이트 요청 DTO
 */
data class UserStatusUpdateRequest(
    val status: Status
)
