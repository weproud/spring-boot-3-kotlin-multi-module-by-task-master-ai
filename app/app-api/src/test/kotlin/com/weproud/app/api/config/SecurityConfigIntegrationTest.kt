package com.weproud.app.api.config

import com.weproud.app.api.config.security.CustomUserDetailsService
import com.weproud.app.api.config.security.JwtAuthenticationFilter
import com.weproud.domain.common.model.UserRole
import com.weproud.domain.common.model.UserStatus
import com.weproud.domain.rds.entity.UserEntity
import com.weproud.domain.rds.repository.UserRepository
import com.weproud.framework.provider.jwt.JwtProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.ZonedDateTime
import java.util.*

@WebMvcTest
@Import(SecurityConfig::class, JwtAuthenticationFilter::class, CustomUserDetailsService::class)
class SecurityConfigIntegrationTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var jwtProvider: JwtProvider

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()

        // Mock user repository
        val userEntity = UserEntity(
            id = 1L,
            email = "test@example.com",
            password = "encoded_password",
            name = "Test User",
            role = UserRole.USER,
            status = UserStatus.ACTIVE,
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity))
    }

    @Test
    fun `public endpoints should be accessible without authentication`() {
        mockMvc.perform(get("/api/public/test"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
        
        mockMvc.perform(get("/api/auth/login"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
    }

    @Test
    fun `protected endpoints should require authentication`() {
        mockMvc.perform(get("/api/user/profile"))
            .andExpect(status().isUnauthorized())
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `user endpoints should be accessible with USER role`() {
        mockMvc.perform(get("/api/user/profile"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun `teacher endpoints should not be accessible with USER role`() {
        mockMvc.perform(get("/api/teacher/users"))
            .andExpect(status().isForbidden())
    }

    @Test
    @WithMockUser(roles = ["TEACHER"])
    fun `teacher endpoints should be accessible with TEACHER role`() {
        mockMvc.perform(get("/api/teacher/users"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
    }

    @Test
    @WithMockUser(roles = ["TEACHER"])
    fun `admin endpoints should not be accessible with TEACHER role`() {
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isForbidden())
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `admin endpoints should be accessible with ADMIN role`() {
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `admin should have access to teacher endpoints due to role hierarchy`() {
        mockMvc.perform(get("/api/teacher/users"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `admin should have access to user endpoints due to role hierarchy`() {
        mockMvc.perform(get("/api/user/profile"))
            .andExpect(status().isNotFound()) // 404 because endpoint doesn't exist, but not 401 or 403
    }
}
