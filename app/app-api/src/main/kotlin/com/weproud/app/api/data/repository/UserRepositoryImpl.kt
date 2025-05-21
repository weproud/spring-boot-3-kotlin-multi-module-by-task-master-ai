package com.weproud.app.api.data.repository

import com.weproud.app.api.data.mapper.UserMapper
import com.weproud.app.api.domain.model.User
import com.weproud.app.api.domain.repository.UserRepository
import org.springframework.stereotype.Repository

/**
 * 사용자 리포지토리 구현체
 */
@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper
) : UserRepository {
    
    /**
     * 사용자 저장
     */
    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userJpaRepository.save(entity)
        return userMapper.toDomain(savedEntity)
    }
    
    /**
     * ID로 사용자 조회
     */
    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id)
            .map { userMapper.toDomain(it) }
            .orElse(null)
    }
    
    /**
     * 이메일로 사용자 조회
     */
    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)
            ?.let { userMapper.toDomain(it) }
    }
    
    /**
     * 모든 사용자 조회
     */
    override fun findAll(): List<User> {
        return userJpaRepository.findAll()
            .map { userMapper.toDomain(it) }
    }
    
    /**
     * 사용자 삭제
     */
    override fun delete(id: Long) {
        userJpaRepository.deleteById(id)
    }
    
    /**
     * 이메일 중복 확인
     */
    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}
