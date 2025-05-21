package com.weproud.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Jackson 설정 클래스
 */
@Configuration
class JacksonConfig {

    /**
     * ObjectMapper 빈 생성
     */
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            // Kotlin 모듈 등록
            registerKotlinModule()
            // Java 8 날짜/시간 모듈 등록
            registerModule(JavaTimeModule())
            // 날짜/시간을 타임스탬프가 아닌 ISO-8601 형식으로 직렬화
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }
}
