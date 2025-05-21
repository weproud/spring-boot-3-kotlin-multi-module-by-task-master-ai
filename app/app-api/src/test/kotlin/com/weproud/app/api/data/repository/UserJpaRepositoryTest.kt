package com.weproud.app.api.data.repository

import com.weproud.app.api.data.entity.UserEntity
import com.weproud.app.api.data.entity.UserStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class UserJpaRepositoryTest {
    
    @Autowired
    private lateinit var userJpaRepository: UserJpaRepository
    
    @Autowired
    private lateinit var entityManager: TestEntityManager
    
    @Test
    fun `사용자 저장 및 조회 테스트`() {
        // given
        val user = UserEntity(
            email = "test@example.com",
            name = "Test User",
            password = "encoded_password"
        )
        
        // when
        val savedUser = userJpaRepository.save(user)
        entityManager.flush()
        entityManager.clear()
        
        // then
        val foundUser = userJpaRepository.findById(savedUser.id!!).orElse(null)
        assertNotNull(foundUser)
        assertEquals(user.email, foundUser.email)
        assertEquals(user.name, foundUser.name)
        assertEquals(user.password, foundUser.password)
        assertEquals(UserStatus.ACTIVE, foundUser.userStatus)
    }
    
    @Test
    fun `이메일로 사용자 조회 테스트`() {
        // given
        val email = "test@example.com"
        val user = UserEntity(
            email = email,
            name = "Test User",
            password = "encoded_password"
        )
        
        userJpaRepository.save(user)
        entityManager.flush()
        entityManager.clear()
        
        // when
        val foundUser = userJpaRepository.findByEmail(email)
        
        // then
        assertNotNull(foundUser)
        assertEquals(email, foundUser!!.email)
    }
    
    @Test
    fun `이메일 존재 여부 확인 테스트`() {
        // given
        val email = "test@example.com"
        val user = UserEntity(
            email = email,
            name = "Test User",
            password = "encoded_password"
        )
        
        userJpaRepository.save(user)
        entityManager.flush()
        entityManager.clear()
        
        // when & then
        assertTrue(userJpaRepository.existsByEmail(email))
        assertFalse(userJpaRepository.existsByEmail("nonexistent@example.com"))
    }
    
    @Test
    fun `사용자 삭제 테스트`() {
        // given
        val user = UserEntity(
            email = "test@example.com",
            name = "Test User",
            password = "encoded_password"
        )
        
        val savedUser = userJpaRepository.save(user)
        entityManager.flush()
        entityManager.clear()
        
        // when
        userJpaRepository.deleteById(savedUser.id!!)
        entityManager.flush()
        entityManager.clear()
        
        // then
        assertFalse(userJpaRepository.existsById(savedUser.id!!))
    }
}
