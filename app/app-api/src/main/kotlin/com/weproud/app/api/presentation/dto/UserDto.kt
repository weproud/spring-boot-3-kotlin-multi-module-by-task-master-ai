package com.weproud.app.api.presentation.dto

import com.weproud.app.api.domain.model.UserStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * 사용자 등록 요청 DTO
 */
data class RegisterUserRequest(
    @field:NotBlank(message = "이메일은 필수 입력값입니다.")
    @field:Email(message = "유효한 이메일 형식이 아닙니다.")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    val password: String,
    
    @field:NotBlank(message = "이름은 필수 입력값입니다.")
    val name: String
)

/**
 * 사용자 로그인 요청 DTO
 */
data class LoginUserRequest(
    @field:NotBlank(message = "이메일은 필수 입력값입니다.")
    @field:Email(message = "유효한 이메일 형식이 아닙니다.")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수 입력값입니다.")
    val password: String
)

/**
 * 사용자 로그인 응답 DTO
 */
data class LoginUserResponse(
    val token: String,
    val userId: Long,
    val email: String,
    val name: String,
    val roles: List<String>
)

/**
 * 사용자 정보 응답 DTO
 */
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val status: UserStatus,
    val roles: List<String>
)

/**
 * 사용자 정보 수정 요청 DTO
 */
data class UpdateUserRequest(
    val name: String? = null,
    
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    val password: String? = null
)
