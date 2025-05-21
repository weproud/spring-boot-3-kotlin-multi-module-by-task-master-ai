package com.weproud.app.api.data.mapper

import com.weproud.app.api.data.entity.UserEntity
import com.weproud.app.api.data.entity.UserStatus as EntityUserStatus
import com.weproud.app.api.domain.model.User
import com.weproud.app.api.domain.model.UserStatus as DomainUserStatus
import org.springframework.stereotype.Component

/**
 * 사용자 매퍼
 */
@Component
class UserMapper {
    
    /**
     * 도메인 모델을 엔티티로 변환
     */
    fun toEntity(user: User): UserEntity {
        return UserEntity(
            email = user.email,
            name = user.name,
            password = user.password,
            userStatus = mapToEntityStatus(user.status),
            roles = user.roles.toMutableSet()
        ).apply {
            // ID가 있는 경우에만 설정 (BaseEntity에서 ID는 val이므로 생성자에서만 설정 가능)
            user.id?.let { id ->
                // 리플렉션을 사용하여 ID 설정 (실제 프로덕션 코드에서는 다른 방법 사용 권장)
                val field = BaseEntity::class.java.getDeclaredField("id")
                field.isAccessible = true
                field.set(this, id)
            }
        }
    }
    
    /**
     * 엔티티를 도메인 모델로 변환
     */
    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            email = entity.email,
            name = entity.name,
            password = entity.password,
            roles = entity.roles.toList(),
            status = mapToDomainStatus(entity.userStatus),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
    
    /**
     * 도메인 상태를 엔티티 상태로 변환
     */
    private fun mapToEntityStatus(status: DomainUserStatus): EntityUserStatus {
        return when (status) {
            DomainUserStatus.ACTIVE -> EntityUserStatus.ACTIVE
            DomainUserStatus.INACTIVE -> EntityUserStatus.INACTIVE
            DomainUserStatus.DELETED -> EntityUserStatus.DELETED
        }
    }
    
    /**
     * 엔티티 상태를 도메인 상태로 변환
     */
    private fun mapToDomainStatus(status: EntityUserStatus): DomainUserStatus {
        return when (status) {
            EntityUserStatus.ACTIVE -> DomainUserStatus.ACTIVE
            EntityUserStatus.INACTIVE -> DomainUserStatus.INACTIVE
            EntityUserStatus.DELETED -> DomainUserStatus.DELETED
        }
    }
}
