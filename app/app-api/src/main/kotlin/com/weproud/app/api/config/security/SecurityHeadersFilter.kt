package com.weproud.app.api.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

/**
 * 보안 헤더 필터
 */
class SecurityHeadersFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // XSS 방지를 위한 Content-Security-Policy 설정
        response.setHeader(
            "Content-Security-Policy",
            "default-src 'self'; script-src 'self' https://trusted-cdn.com; " +
            "style-src 'self' https://trusted-cdn.com; img-src 'self' data:; " +
            "font-src 'self'; connect-src 'self'; frame-src 'none'; object-src 'none'"
        )

        // 클릭재킹 방지를 위한 X-Frame-Options 설정
        response.setHeader("X-Frame-Options", "DENY")

        // MIME 스니핑 방지를 위한 X-Content-Type-Options 설정
        response.setHeader("X-Content-Type-Options", "nosniff")

        // XSS 필터 활성화를 위한 X-XSS-Protection 설정
        response.setHeader("X-XSS-Protection", "1; mode=block")

        // HTTPS 사용 강제를 위한 Strict-Transport-Security 설정
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains")

        // 참조자 정보 제한을 위한 Referrer-Policy 설정
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin")

        // 권한 정책 설정
        response.setHeader(
            "Permissions-Policy",
            "camera=(), microphone=(), geolocation=(), payment=()"
        )

        filterChain.doFilter(request, response)
    }
}
