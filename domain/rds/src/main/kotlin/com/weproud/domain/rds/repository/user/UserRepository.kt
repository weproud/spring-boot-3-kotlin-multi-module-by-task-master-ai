package com.weproud.domain.rds.repository.user

import com.weproud.domain.rds.entity.user.UserEntity
import com.weproud.domain.rds.entity.user.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * 사용자 리포지토리
 */
@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    
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
    
    /**
     * 역할로 사용자 목록 조회
     */
    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r = :role")
    fun findByRole(role: String): List<UserEntity>
}
