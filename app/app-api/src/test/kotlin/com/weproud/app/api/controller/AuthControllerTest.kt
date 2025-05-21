package com.weproud.app.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.weproud.app.api.controller.dto.LoginRequest
import com.weproud.app.api.controller.dto.RegisterRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `회원가입 요청 시 성공적으로 처리되어야 한다`() {
        // given
        val request = RegisterRequest(
            email = "test@example.com",
            password = "password123",
            name = "테스트 사용자"
        )

        // when & then
        mockMvc.perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
    }

    @Test
    fun `로그인 요청 시 올바른 사용자 정보와 토큰이 반환되어야 한다`() {
        // given
        val request = LoginRequest(
            email = "user@example.com",
            password = "password123"
        )

        // when & then
        mockMvc.perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.token").exists())
            .andExpect(jsonPath("$.data.userId").value(1))
            .andExpect(jsonPath("$.data.email").value("user@example.com"))
    }
}
