package com.weproud.framework.client.kakao

import com.weproud.framework.client.base.BaseClient
import com.weproud.framework.client.kakao.dto.KakaoTokenResponse
import com.weproud.framework.client.kakao.dto.KakaoUserInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

/**
 * 카카오 API 클라이언트
 */
@Component
class KakaoClient(
    webClient: WebClient,
    @Value("\${kakao.client-id}") private val clientId: String,
    @Value("\${kakao.client-secret}") private val clientSecret: String,
    @Value("\${kakao.redirect-uri}") private val redirectUri: String
) : BaseClient(webClient) {
    
    private val AUTH_HOST = "https://kauth.kakao.com"
    private val API_HOST = "https://kapi.kakao.com"
    
    /**
     * 카카오 액세스 토큰 요청
     */
    fun getToken(code: String): KakaoTokenResponse {
        val uri = "$AUTH_HOST/oauth/token?grant_type=authorization_code&client_id=$clientId&client_secret=$clientSecret&code=$code&redirect_uri=$redirectUri"
        
        return post(
            uri = uri,
            body = emptyMap<String, String>(),
            responseType = KakaoTokenResponse::class.java,
            headers = mapOf("Content-Type" to "application/x-www-form-urlencoded;charset=utf-8")
        )
    }
    
    /**
     * 카카오 사용자 정보 요청
     */
    fun getUserInfo(accessToken: String): KakaoUserInfoResponse {
        val uri = "$API_HOST/v2/user/me"
        
        return get(
            uri = uri,
            responseType = KakaoUserInfoResponse::class.java,
            headers = mapOf("Authorization" to "Bearer $accessToken")
        )
    }
}
