package com.weproud.app.api.user.mapper

import com.weproud.app.api.user.dto.UserLoginResponse
import com.weproud.app.api.user.dto.UserResponse
import com.weproud.domain.rds.entity.user.UserEntity
import org.springframework.stereotype.Component

/**
 * 사용자 매퍼
 */
@Component
class UserMapper {
    
    /**
     * UserEntity를 UserResponse로 변환
     */
    fun toUserResponse(userEntity: UserEntity): UserResponse {
        return UserResponse(
            id = userEntity.id!!,
            email = userEntity.email,
            name = userEntity.name,
            status = userEntity.userStatus,
            roles = userEntity.roles.toList(),
            createdAt = userEntity.createdAt,
            updatedAt = userEntity.updatedAt
        )
    }
    
    /**
     * UserEntity와 토큰을 UserLoginResponse로 변환
     */
    fun toUserLoginResponse(userEntity: UserEntity, token: String): UserLoginResponse {
        return UserLoginResponse(
            token = token,
            userId = userEntity.id!!,
            email = userEntity.email,
            name = userEntity.name,
            roles = userEntity.roles.toList()
        )
    }
}
