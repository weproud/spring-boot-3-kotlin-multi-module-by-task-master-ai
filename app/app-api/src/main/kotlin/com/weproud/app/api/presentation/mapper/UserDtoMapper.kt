package com.weproud.app.api.presentation.mapper

import com.weproud.app.api.domain.model.User
import com.weproud.app.api.domain.usecase.LoginUserUseCase
import com.weproud.app.api.presentation.dto.LoginUserResponse
import com.weproud.app.api.presentation.dto.UserResponse
import org.springframework.stereotype.Component

/**
 * 사용자 DTO 매퍼
 */
@Component
class UserDtoMapper {
    
    /**
     * 도메인 모델을 응답 DTO로 변환
     */
    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id ?: throw IllegalArgumentException("사용자 ID가 없습니다."),
            email = user.email,
            name = user.name,
            status = user.status,
            roles = user.roles
        )
    }
    
    /**
     * 로그인 결과를 응답 DTO로 변환
     */
    fun toLoginResponse(loginResult: LoginUserUseCase.LoginResult): LoginUserResponse {
        return LoginUserResponse(
            token = loginResult.token,
            userId = loginResult.user.id ?: throw IllegalArgumentException("사용자 ID가 없습니다."),
            email = loginResult.user.email,
            name = loginResult.user.name,
            roles = loginResult.user.roles
        )
    }
}
