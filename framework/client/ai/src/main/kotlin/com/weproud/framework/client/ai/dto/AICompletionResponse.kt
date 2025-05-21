package com.weproud.framework.client.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * AI 텍스트 완성 응답 DTO
 */
data class AICompletionResponse(
    val id: String,
    
    @JsonProperty("object")
    val objectType: String,
    
    val created: Long,
    
    val model: String,
    
    val choices: List<Choice>,
    
    val usage: Usage
) {
    data class Choice(
        val text: String,
        
        val index: Int,
        
        @JsonProperty("finish_reason")
        val finishReason: String
    )
    
    data class Usage(
        @JsonProperty("prompt_tokens")
        val promptTokens: Int,
        
        @JsonProperty("completion_tokens")
        val completionTokens: Int,
        
        @JsonProperty("total_tokens")
        val totalTokens: Int
    )
}
