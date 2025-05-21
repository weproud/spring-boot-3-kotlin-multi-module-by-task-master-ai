package com.weproud.domain.common.model

import com.weproud.domain.common.constant.Status
import java.time.LocalDateTime

/**
 * 모든 모델 클래스의 기본 인터페이스
 */
interface BaseModel {
    val id: Long?
    val status: Status
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}
