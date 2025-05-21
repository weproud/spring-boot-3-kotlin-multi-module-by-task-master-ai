package com.weproud.app.api.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(HelloController::class)
class HelloControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Hello 엔드포인트는 성공 응답과 메시지를 반환해야 한다`() {
        mockMvc.perform(get("/api/hello"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.message").value("Hello, Spring Boot 3 with Kotlin!"))
            .andExpect(jsonPath("$.data.version").value("1.0.0"))
            .andExpect(jsonPath("$.data.framework").value("Spring Boot 3.4.5"))
            .andExpect(jsonPath("$.data.language").value("Kotlin 1.9.25"))
    }
}
