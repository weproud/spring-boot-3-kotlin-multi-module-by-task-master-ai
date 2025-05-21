package com.weproud.app.api.domain.usecase

import com.weproud.app.api.domain.exception.DomainException
import com.weproud.app.api.domain.model.User
import com.weproud.app.api.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class RegisterUserUseCaseImplTest {
    
    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val registerUserUseCase = RegisterUserUseCaseImpl(userRepository, passwordEncoder)
    
    @Test
    fun `이메일이 중복되면 예외가 발생해야 한다`() {
        // given
        val email = "test@example.com"
        val password = "password123"
        val name = "Test User"
        
        every { userRepository.existsByEmail(email) } returns true
        
        // when & then
        val exception = assertThrows<DomainException> {
            registerUserUseCase.execute(email, password, name)
        }
        
        assertEquals("EMAIL_ALREADY_EXISTS", exception.errorCode)
        assertEquals(400, exception.status)
    }
    
    @Test
    fun `유효한 정보로 사용자 등록이 성공해야 한다`() {
        // given
        val email = "test@example.com"
        val password = "password123"
        val name = "Test User"
        val encodedPassword = "encoded_password"
        
        every { userRepository.existsByEmail(email) } returns false
        every { passwordEncoder.encode(password) } returns encodedPassword
        every { userRepository.save(any()) } answers {
            val user = firstArg<User>()
            user.copy(id = 1L)
        }
        
        // when
        val result = registerUserUseCase.execute(email, password, name)
        
        // then
        assertEquals(1L, result.id)
        assertEquals(email, result.email)
        assertEquals(name, result.name)
        assertEquals(encodedPassword, result.password)
        
        verify { userRepository.existsByEmail(email) }
        verify { passwordEncoder.encode(password) }
        verify { userRepository.save(any()) }
    }
}
