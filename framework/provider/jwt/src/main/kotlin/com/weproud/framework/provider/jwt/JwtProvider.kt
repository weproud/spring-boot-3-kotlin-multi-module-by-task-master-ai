package com.weproud.framework.provider.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

/**
 * JWT 토큰 제공자
 */
@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expirationTime: Long
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    
    /**
     * JWT 토큰 생성
     */
    fun generateToken(userId: Long, roles: List<String>): String {
        val now = Date()
        val expiration = Date(now.time + expirationTime)
        
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("roles", roles)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
    
    /**
     * JWT 토큰 검증
     */
    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * JWT 토큰에서 사용자 ID 추출
     */
    fun getUserId(token: String): Long {
        return getClaims(token).subject.toLong()
    }
    
    /**
     * JWT 토큰에서 역할 목록 추출
     */
    @Suppress("UNCHECKED_CAST")
    fun getRoles(token: String): List<String> {
        return getClaims(token)["roles"] as List<String>
    }
    
    /**
     * JWT 토큰에서 클레임 추출
     */
    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
