package com.weproud.framework.client.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 카카오 토큰 응답 DTO
 */
data class KakaoTokenResponse(
    @JsonProperty("token_type")
    val tokenType: String,
    
    @JsonProperty("access_token")
    val accessToken: String,
    
    @JsonProperty("expires_in")
    val expiresIn: Int,
    
    @JsonProperty("refresh_token")
    val refreshToken: String,
    
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int
)
