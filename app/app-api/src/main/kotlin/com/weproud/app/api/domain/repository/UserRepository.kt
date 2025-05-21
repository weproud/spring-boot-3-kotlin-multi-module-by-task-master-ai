package com.weproud.app.api.domain.repository

import com.weproud.app.api.domain.model.User

/**
 * 사용자 리포지토리 인터페이스
 */
interface UserRepository {
    /**
     * 사용자 저장
     */
    fun save(user: User): User
    
    /**
     * ID로 사용자 조회
     */
    fun findById(id: Long): User?
    
    /**
     * 이메일로 사용자 조회
     */
    fun findByEmail(email: String): User?
    
    /**
     * 모든 사용자 조회
     */
    fun findAll(): List<User>
    
    /**
     * 사용자 삭제
     */
    fun delete(id: Long)
    
    /**
     * 이메일 중복 확인
     */
    fun existsByEmail(email: String): Boolean
}
