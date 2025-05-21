package com.weproud.framework.client.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * AI 텍스트 완성 요청 DTO
 */
data class AICompletionRequest(
    val prompt: String,
    
    @JsonProperty("max_tokens")
    val maxTokens: Int,
    
    val temperature: Double = 0.7,
    
    @JsonProperty("top_p")
    val topP: Double = 1.0,
    
    val n: Int = 1,
    
    val stream: Boolean = false,
    
    @JsonProperty("stop")
    val stopSequences: List<String>? = null
)
