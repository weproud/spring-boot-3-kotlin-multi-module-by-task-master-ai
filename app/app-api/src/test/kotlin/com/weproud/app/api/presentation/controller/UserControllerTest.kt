package com.weproud.app.api.presentation.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.weproud.app.api.domain.model.User
import com.weproud.app.api.domain.usecase.*
import com.weproud.app.api.presentation.dto.LoginUserRequest
import com.weproud.app.api.presentation.dto.RegisterUserRequest
import com.weproud.app.api.presentation.dto.UserResponse
import com.weproud.app.api.presentation.mapper.UserDtoMapper
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

@WebMvcTest(UserController::class)
class UserControllerTest {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    
    @MockBean
    private lateinit var registerUserUseCase: RegisterUserUseCase
    
    @MockBean
    private lateinit var loginUserUseCase: LoginUserUseCase
    
    @MockBean
    private lateinit var getUserUseCase: GetUserUseCase
    
    @MockBean
    private lateinit var updateUserUseCase: UpdateUserUseCase
    
    @MockBean
    private lateinit var userDtoMapper: UserDtoMapper
    
    @Test
    fun `사용자 등록 요청이 성공적으로 처리되어야 한다`() {
        // given
        val request = RegisterUserRequest(
            email = "test@example.com",
            password = "password123",
            name = "Test User"
        )
        
        val user = User(
            id = 1L,
            email = request.email,
            name = request.name,
            password = "encoded_password"
        )
        
        val response = UserResponse(
            id = 1L,
            email = request.email,
            name = request.name,
            status = com.weproud.app.api.domain.model.UserStatus.ACTIVE,
            roles = listOf("USER")
        )
        
        `when`(registerUserUseCase.execute(request.email, request.password, request.name)).thenReturn(user)
        `when`(userDtoMapper.toResponse(user)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.email").value(request.email))
            .andExpect(jsonPath("$.data.name").value(request.name))
    }
    
    @Test
    @WithMockUser
    fun `현재 사용자 정보 조회 요청이 성공적으로 처리되어야 한다`() {
        // given
        val userId = 1L
        
        val user = User(
            id = userId,
            email = "test@example.com",
            name = "Test User",
            password = "encoded_password"
        )
        
        val response = UserResponse(
            id = userId,
            email = user.email,
            name = user.name,
            status = com.weproud.app.api.domain.model.UserStatus.ACTIVE,
            roles = listOf("USER")
        )
        
        `when`(getUserUseCase.execute(userId)).thenReturn(user)
        `when`(userDtoMapper.toResponse(user)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            get("/api/users/me")
                .with(user(userId.toString()))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(userId))
            .andExpect(jsonPath("$.data.email").value(user.email))
            .andExpect(jsonPath("$.data.name").value(user.name))
    }
}
