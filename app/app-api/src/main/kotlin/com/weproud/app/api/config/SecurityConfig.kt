package com.weproud.app.api.config

import com.weproud.app.api.config.security.CustomUserDetailsService
import com.weproud.app.api.config.security.JwtAuthenticationFilter
import com.weproud.app.api.config.security.SecurityHeadersFilter
import com.weproud.framework.provider.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Spring Security 설정 클래스
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfig(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: CustomUserDetailsService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // CSRF 보호 설정
            .csrf { csrf ->
                // API 경로는 CSRF 보호 비활성화 (JWT 기반 인증에서는 일반적으로 비활성화)
                csrf.ignoringRequestMatchers("/api/**")
                // 나머지 경로는 CSRF 보호 활성화
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            }

            // CORS 설정 활성화
            .cors { it.configurationSource(corsConfigurationSource()) }

            // 기본 HTTP 기본 인증 비활성화
            .httpBasic { it.disable() }

            // 폼 로그인 비활성화
            .formLogin { it.disable() }

            // 세션 관리 설정 (JWT 기반 인증에서는 세션을 사용하지 않음)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

            // 보안 헤더 설정
            .headers { headers ->
                headers
                    .contentSecurityPolicy { csp ->
                        csp.policyDirectives(
                            "default-src 'self'; script-src 'self' https://trusted-cdn.com; " +
                            "style-src 'self' https://trusted-cdn.com; img-src 'self' data:; " +
                            "font-src 'self'; connect-src 'self'; frame-src 'none'; object-src 'none'"
                        )
                    }
                    .frameOptions { it.deny() }
                    .xssProtection { it.block(true) }
                    .contentTypeOptions { }
                    .referrerPolicy { it.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN) }
                    .permissionsPolicy { permissions ->
                        permissions.policy(
                            "camera=(), microphone=(), geolocation=(), payment=()"
                        )
                    }
            }

            // 요청 인증 설정
            .authorizeHttpRequests { auth ->
                auth
                    // 공개 엔드포인트 설정
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/actuator/**").permitAll()
                    // 역할 기반 접근 제어
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                    .requestMatchers("/api/user/**").hasRole("USER")
                    // 나머지 요청은 인증 필요
                    .anyRequest().authenticated()
            }

            // 필터 추가
            .addFilterBefore(
                JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterAfter(
                SecurityHeadersFilter(),
                JwtAuthenticationFilter::class.java
            )

            // 예외 처리
            .exceptionHandling {
                it.authenticationEntryPoint { request, response, authException ->
                    response.sendError(401, "인증이 필요합니다")
                }
                it.accessDeniedHandler { request, response, accessDeniedException ->
                    response.sendError(403, "접근 권한이 없습니다")
                }
            }

        return http.build()
    }

    /**
     * 인증 관리자 설정
     */
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
        return authManagerBuilder.build()
    }

    /**
     * 비밀번호 인코더 빈 등록
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * CORS 설정 소스 빈 등록
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // 실제 환경에서는 특정 도메인으로 제한해야 함
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type", "X-Requested-With")
        configuration.exposedHeaders = listOf("Authorization")
        configuration.allowCredentials = false
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
