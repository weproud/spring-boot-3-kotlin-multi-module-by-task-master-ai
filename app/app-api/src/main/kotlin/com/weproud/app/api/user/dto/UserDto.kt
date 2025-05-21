package com.weproud.app.api.user.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.weproud.domain.rds.entity.user.UserStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

/**
 * 사용자 등록 요청 DTO
 */
data class UserRegisterRequest(
    @field:NotBlank(message = "이메일은 필수 입력값입니다.")
    @field:Email(message = "유효한 이메일 형식이 아닙니다.")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @field:Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    val password: String,
    
    @field:NotBlank(message = "이름은 필수 입력값입니다.")
    @field:Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    val name: String
)

/**
 * 사용자 로그인 요청 DTO
 */
data class UserLoginRequest(
    @field:NotBlank(message = "이메일은 필수 입력값입니다.")
    @field:Email(message = "유효한 이메일 형식이 아닙니다.")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수 입력값입니다.")
    val password: String
)

/**
 * 사용자 로그인 응답 DTO
 */
data class UserLoginResponse(
    val token: String,
    val userId: Long,
    val email: String,
    val name: String,
    val roles: List<String>
)

/**
 * 사용자 정보 응답 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val status: UserStatus,
    val roles: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

/**
 * 사용자 정보 수정 요청 DTO
 */
data class UserUpdateRequest(
    @field:Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    val name: String? = null,
    
    @field:Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    val password: String? = null
)
