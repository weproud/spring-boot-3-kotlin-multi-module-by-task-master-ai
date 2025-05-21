package com.weproud.app.api.data.repository

import com.weproud.app.api.data.entity.UserEntity
import com.weproud.app.api.data.entity.UserStatus
import com.weproud.app.api.data.mapper.UserMapper
import com.weproud.app.api.domain.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.*

class UserRepositoryImplTest {
    
    private val userJpaRepository = mockk<UserJpaRepository>()
    private val userMapper = mockk<UserMapper>()
    private val userRepository = UserRepositoryImpl(userJpaRepository, userMapper)
    
    @Test
    fun `ID로 사용자 조회 시 결과가 있으면 도메인 모델로 변환해야 한다`() {
        // given
        val id = 1L
        val userEntity = mockk<UserEntity>()
        val user = User(
            id = id,
            email = "test@example.com",
            name = "Test User",
            password = "encoded_password"
        )
        
        every { userJpaRepository.findById(id) } returns Optional.of(userEntity)
        every { userMapper.toDomain(userEntity) } returns user
        
        // when
        val result = userRepository.findById(id)
        
        // then
        assertEquals(user, result)
        verify { userJpaRepository.findById(id) }
        verify { userMapper.toDomain(userEntity) }
    }
    
    @Test
    fun `ID로 사용자 조회 시 결과가 없으면 null을 반환해야 한다`() {
        // given
        val id = 1L
        
        every { userJpaRepository.findById(id) } returns Optional.empty()
        
        // when
        val result = userRepository.findById(id)
        
        // then
        assertNull(result)
        verify { userJpaRepository.findById(id) }
    }
    
    @Test
    fun `이메일로 사용자 조회 시 결과가 있으면 도메인 모델로 변환해야 한다`() {
        // given
        val email = "test@example.com"
        val userEntity = mockk<UserEntity>()
        val user = User(
            id = 1L,
            email = email,
            name = "Test User",
            password = "encoded_password"
        )
        
        every { userJpaRepository.findByEmail(email) } returns userEntity
        every { userMapper.toDomain(userEntity) } returns user
        
        // when
        val result = userRepository.findByEmail(email)
        
        // then
        assertEquals(user, result)
        verify { userJpaRepository.findByEmail(email) }
        verify { userMapper.toDomain(userEntity) }
    }
    
    @Test
    fun `이메일로 사용자 조회 시 결과가 없으면 null을 반환해야 한다`() {
        // given
        val email = "test@example.com"
        
        every { userJpaRepository.findByEmail(email) } returns null
        
        // when
        val result = userRepository.findByEmail(email)
        
        // then
        assertNull(result)
        verify { userJpaRepository.findByEmail(email) }
    }
}
