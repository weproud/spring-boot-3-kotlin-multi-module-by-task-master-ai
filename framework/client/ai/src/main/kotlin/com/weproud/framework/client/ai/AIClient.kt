package com.weproud.framework.client.ai

import com.weproud.framework.client.ai.dto.AICompletionRequest
import com.weproud.framework.client.ai.dto.AICompletionResponse
import com.weproud.framework.client.base.BaseClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

/**
 * AI API 클라이언트
 */
@Component
class AIClient(
    webClient: WebClient,
    @Value("\${ai.api-key}") private val apiKey: String,
    @Value("\${ai.api-url}") private val apiUrl: String
) : BaseClient(webClient) {
    
    /**
     * AI 텍스트 완성 요청
     */
    fun getCompletion(prompt: String, maxTokens: Int = 100): AICompletionResponse {
        val request = AICompletionRequest(
            prompt = prompt,
            maxTokens = maxTokens
        )
        
        return post(
            uri = apiUrl,
            body = request,
            responseType = AICompletionResponse::class.java,
            headers = mapOf(
                "Content-Type" to "application/json",
                "Authorization" to "Bearer $apiKey"
            )
        )
    }
}
