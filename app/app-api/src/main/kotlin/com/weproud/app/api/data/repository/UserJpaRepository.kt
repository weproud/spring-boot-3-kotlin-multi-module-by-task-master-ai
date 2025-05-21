package com.weproud.app.api.data.repository

import com.weproud.app.api.data.entity.UserEntity
import com.weproud.app.api.data.entity.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * 사용자 JPA 리포지토리
 */
@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    /**
     * 이메일로 사용자 조회
     */
    fun findByEmail(email: String): UserEntity?
    
    /**
     * 이메일과 상태로 사용자 조회
     */
    fun findByEmailAndUserStatus(email: String, status: UserStatus): UserEntity?
    
    /**
     * 이메일 존재 여부 확인
     */
    fun existsByEmail(email: String): Boolean
}
