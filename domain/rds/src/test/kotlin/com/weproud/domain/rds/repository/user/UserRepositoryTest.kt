package com.weproud.domain.rds.repository.user

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.user.UserEntity
import com.weproud.domain.rds.entity.user.UserStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    
    @Autowired
    private lateinit var userRepository: UserRepository
    
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
        val savedUser = userRepository.save(user)
        entityManager.flush()
        entityManager.clear()
        
        // then
        val foundUser = userRepository.findById(savedUser.id!!).orElse(null)
        assertNotNull(foundUser)
        assertEquals(user.email, foundUser.email)
        assertEquals(user.name, foundUser.name)
        assertEquals(user.password, foundUser.password)
        assertEquals(UserStatus.ACTIVE, foundUser.userStatus)
        assertEquals(Status.ACTIVE, foundUser.status)
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
        
        userRepository.save(user)
        entityManager.flush()
        entityManager.clear()
        
        // when
        val foundUser = userRepository.findByEmail(email)
        
        // then
        assertNotNull(foundUser)
        assertEquals(email, foundUser!!.email)
    }
    
    @Test
    fun `역할로 사용자 목록 조회 테스트`() {
        // given
        val user1 = UserEntity(
            email = "user1@example.com",
            name = "User 1",
            password = "encoded_password"
        )
        user1.addRole("USER")
        
        val user2 = UserEntity(
            email = "user2@example.com",
            name = "User 2",
            password = "encoded_password"
        )
        user2.addRole("USER")
        user2.addRole("ADMIN")
        
        userRepository.saveAll(listOf(user1, user2))
        entityManager.flush()
        entityManager.clear()
        
        // when
        val usersWithUserRole = userRepository.findByRole("USER")
        val usersWithAdminRole = userRepository.findByRole("ADMIN")
        
        // then
        assertEquals(2, usersWithUserRole.size)
        assertEquals(1, usersWithAdminRole.size)
        assertEquals(user2.email, usersWithAdminRole[0].email)
    }
}
