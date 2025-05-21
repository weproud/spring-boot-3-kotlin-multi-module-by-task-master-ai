package com.weproud.app.api.config.security

import com.weproud.domain.common.model.UserRole
import com.weproud.domain.common.model.UserStatus
import com.weproud.domain.rds.entity.UserEntity
import com.weproud.domain.rds.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.time.ZonedDateTime
import java.util.*

class CustomUserDetailsServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val userDetailsService = CustomUserDetailsService(userRepository)

    @Test
    fun `loadUserByUsername should return UserDetails when user exists`() {
        // Given
        val email = "test@example.com"
        val userEntity = UserEntity(
            id = 1L,
            email = email,
            password = "encoded_password",
            name = "Test User",
            role = UserRole.USER,
            status = UserStatus.ACTIVE,
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )
        
        every { userRepository.findByEmail(email) } returns Optional.of(userEntity)

        // When
        val userDetails = userDetailsService.loadUserByUsername(email)

        // Then
        assertEquals(email, userDetails.username)
        assertEquals("encoded_password", userDetails.password)
        assertEquals(1, userDetails.authorities.size)
        assertEquals("ROLE_USER", userDetails.authorities.first().authority)
        assertFalse(userDetails.isAccountExpired)
        assertFalse(userDetails.isAccountLocked)
        assertFalse(userDetails.isCredentialsExpired)
        assertFalse(userDetails.isDisabled)
    }

    @Test
    fun `loadUserByUsername should throw UsernameNotFoundException when user does not exist`() {
        // Given
        val email = "nonexistent@example.com"
        every { userRepository.findByEmail(email) } returns Optional.empty()

        // When & Then
        assertThrows<UsernameNotFoundException> {
            userDetailsService.loadUserByUsername(email)
        }
    }

    @Test
    fun `loadUserByUsername should set account locked when user status is SUSPENDED`() {
        // Given
        val email = "suspended@example.com"
        val userEntity = UserEntity(
            id = 2L,
            email = email,
            password = "encoded_password",
            name = "Suspended User",
            role = UserRole.USER,
            status = UserStatus.SUSPENDED,
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )
        
        every { userRepository.findByEmail(email) } returns Optional.of(userEntity)

        // When
        val userDetails = userDetailsService.loadUserByUsername(email)

        // Then
        assertEquals(email, userDetails.username)
        assertEquals(true, userDetails.isAccountLocked)
    }

    @Test
    fun `loadUserByUsername should set account disabled when user status is not ACTIVE`() {
        // Given
        val email = "inactive@example.com"
        val userEntity = UserEntity(
            id = 3L,
            email = email,
            password = "encoded_password",
            name = "Inactive User",
            role = UserRole.USER,
            status = UserStatus.INACTIVE,
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )
        
        every { userRepository.findByEmail(email) } returns Optional.of(userEntity)

        // When
        val userDetails = userDetailsService.loadUserByUsername(email)

        // Then
        assertEquals(email, userDetails.username)
        assertEquals(true, userDetails.isDisabled)
    }
}
