package com.weproud.app.api.config.security

import com.weproud.framework.provider.jwt.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

/**
 * JWT 인증 필터
 */
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = getTokenFromRequest(request)

            if (token != null) {
                if (jwtProvider.validateToken(token)) {
                    val userId = jwtProvider.getUserId(token)
                    val roles = jwtProvider.getRoles(token)
                    val authorities = roles.map { SimpleGrantedAuthority("ROLE_$it") }

                    val authentication = UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        authorities
                    )

                    SecurityContextHolder.getContext().authentication = authentication
                    logger.debug("사용자 ID: {} 인증 성공", userId)
                } else {
                    logger.debug("유효하지 않은 JWT 토큰")
                }
            }
        } catch (ex: Exception) {
            logger.error("JWT 토큰 처리 중 오류 발생: {}", ex.message)
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    /**
     * 특정 경로에 대해 필터를 적용하지 않도록 설정
     */
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return path.startsWith("/api/auth/") ||
               path.startsWith("/api/public/") ||
               path.startsWith("/swagger-ui/") ||
               path.startsWith("/v3/api-docs/") ||
               path.startsWith("/actuator/")
    }
}
