package com.weproud.app.api.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.weproud.app.api.user.dto.UserLoginRequest
import com.weproud.app.api.user.dto.UserLoginResponse
import com.weproud.app.api.user.dto.UserRegisterRequest
import com.weproud.app.api.user.dto.UserResponse
import com.weproud.app.api.user.service.UserService
import com.weproud.domain.rds.entity.user.UserStatus
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(UserController::class)
class UserControllerTest {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    
    @MockBean
    private lateinit var userService: UserService
    
    @Test
    fun `사용자 등록 요청이 성공적으로 처리되어야 한다`() {
        // given
        val request = UserRegisterRequest(
            email = "test@example.com",
            password = "Password1!",
            name = "Test User"
        )
        
        val response = UserResponse(
            id = 1L,
            email = request.email,
            name = request.name,
            status = UserStatus.ACTIVE,
            roles = listOf("USER"),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        `when`(userService.register(request)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.email").value(request.email))
            .andExpect(jsonPath("$.data.name").value(request.name))
    }
    
    @Test
    fun `사용자 로그인 요청이 성공적으로 처리되어야 한다`() {
        // given
        val request = UserLoginRequest(
            email = "test@example.com",
            password = "Password1!"
        )
        
        val response = UserLoginResponse(
            token = "test.jwt.token",
            userId = 1L,
            email = request.email,
            name = "Test User",
            roles = listOf("USER")
        )
        
        `when`(userService.login(request.email, request.password)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.token").value(response.token))
            .andExpect(jsonPath("$.data.userId").value(response.userId))
            .andExpect(jsonPath("$.data.email").value(response.email))
    }
    
    @Test
    @WithMockUser
    fun `현재 사용자 정보 조회 요청이 성공적으로 처리되어야 한다`() {
        // given
        val userId = 1L
        
        val response = UserResponse(
            id = userId,
            email = "test@example.com",
            name = "Test User",
            status = UserStatus.ACTIVE,
            roles = listOf("USER"),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        `when`(userService.getUserById(userId)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            get("/api/users/me")
                .with(user(userId.toString()))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(userId))
            .andExpect(jsonPath("$.data.email").value(response.email))
            .andExpect(jsonPath("$.data.name").value(response.name))
    }
}
