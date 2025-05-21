package com.weproud.framework.provider.jwt

import io.jsonwebtoken.ExpiredJwtException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class JwtProviderTest {

    private lateinit var jwtProvider: JwtProvider
    private val secretKey = "thisisasecretkeyfortestingpurposesonly12345678901234567890"
    private val expirationTime = 3600000L // 1 hour

    @BeforeEach
    fun setUp() {
        jwtProvider = JwtProvider(secretKey, expirationTime)
    }

    @Test
    fun `generateToken should create valid JWT token with correct claims`() {
        // Given
        val userId = 123L
        val roles = listOf("USER", "ADMIN")

        // When
        val token = jwtProvider.generateToken(userId, roles)

        // Then
        assertNotNull(token)
        assertTrue(token.isNotEmpty())
        assertTrue(jwtProvider.validateToken(token))
        assertEquals(userId, jwtProvider.getUserId(token))
        assertEquals(roles, jwtProvider.getRoles(token))
    }

    @Test
    fun `validateToken should return true for valid token`() {
        // Given
        val userId = 123L
        val roles = listOf("USER")
        val token = jwtProvider.generateToken(userId, roles)

        // When
        val isValid = jwtProvider.validateToken(token)

        // Then
        assertTrue(isValid)
    }

    @Test
    fun `validateToken should return false for invalid token`() {
        // Given
        val invalidToken = "invalid.token.string"

        // When
        val isValid = jwtProvider.validateToken(invalidToken)

        // Then
        assertFalse(isValid)
    }

    @Test
    fun `validateToken should return false for expired token`() {
        // Given
        val expiredJwtProvider = JwtProvider(secretKey, -10000L) // Negative expiration time
        val userId = 123L
        val roles = listOf("USER")
        val token = expiredJwtProvider.generateToken(userId, roles)

        // When
        val isValid = expiredJwtProvider.validateToken(token)

        // Then
        assertFalse(isValid)
    }

    @Test
    fun `getUserId should extract correct user ID from token`() {
        // Given
        val userId = 456L
        val roles = listOf("USER")
        val token = jwtProvider.generateToken(userId, roles)

        // When
        val extractedUserId = jwtProvider.getUserId(token)

        // Then
        assertEquals(userId, extractedUserId)
    }

    @Test
    fun `getRoles should extract correct roles from token`() {
        // Given
        val userId = 789L
        val roles = listOf("USER", "ADMIN", "MANAGER")
        val token = jwtProvider.generateToken(userId, roles)

        // When
        val extractedRoles = jwtProvider.getRoles(token)

        // Then
        assertEquals(roles, extractedRoles)
        assertEquals(3, extractedRoles.size)
        assertTrue(extractedRoles.contains("USER"))
        assertTrue(extractedRoles.contains("ADMIN"))
        assertTrue(extractedRoles.contains("MANAGER"))
    }

    @Test
    fun `getClaims should throw exception for invalid token`() {
        // Given
        val invalidToken = "invalid.token.string"

        // When & Then
        assertThrows<Exception> {
            // Using reflection to access private method for testing
            val method = JwtProvider::class.java.getDeclaredMethod("getClaims", String::class.java)
            method.isAccessible = true
            method.invoke(jwtProvider, invalidToken)
        }
    }
}
