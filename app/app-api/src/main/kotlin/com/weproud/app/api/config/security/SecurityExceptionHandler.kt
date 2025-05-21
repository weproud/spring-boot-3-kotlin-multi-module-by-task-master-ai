package com.weproud.app.api.config.security

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * 보안 관련 예외 처리 핸들러
 */
@RestControllerAdvice
class SecurityExceptionHandler : ResponseEntityExceptionHandler() {

    private val logger = LoggerFactory.getLogger(SecurityExceptionHandler::class.java)

    /**
     * 인증 예외 처리
     */
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        ex: AuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        logger.error("인증 예외 발생: {}", ex.message)
        
        val errorMessage = when (ex) {
            is BadCredentialsException -> "잘못된 인증 정보입니다"
            else -> "인증에 실패했습니다"
        }
        
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(mapOf("error" to errorMessage))
    }

    /**
     * 접근 거부 예외 처리
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        ex: AccessDeniedException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        logger.error("접근 거부 예외 발생: {}", ex.message)
        
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(mapOf("error" to "접근 권한이 없습니다"))
    }

    /**
     * JWT 관련 예외 처리
     */
    @ExceptionHandler(JwtException::class)
    fun handleJwtException(
        ex: JwtException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        logger.error("JWT 예외 발생: {}", ex.message)
        
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(mapOf("error" to ex.message ?: "토큰 처리 중 오류가 발생했습니다"))
    }
}

/**
 * JWT 관련 예외 클래스
 */
class JwtException(message: String) : RuntimeException(message)
