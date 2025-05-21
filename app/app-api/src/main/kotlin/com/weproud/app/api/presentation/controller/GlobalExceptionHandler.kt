package com.weproud.app.api.presentation.controller

import com.weproud.app.api.domain.exception.DomainException
import com.weproud.core.config.ApiResponse
import com.weproud.core.exception.BaseException
import com.weproud.core.exception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 글로벌 예외 핸들러
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val log = LoggerFactory.getLogger(javaClass)
    
    /**
     * 도메인 예외 처리
     */
    @ExceptionHandler(DomainException::class)
    fun handleDomainException(e: DomainException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Domain exception occurred: {}", e.message, e)
        
        return ResponseEntity
            .status(e.status)
            .body(ApiResponse.error(e.errorCode, e.message, e.status))
    }
    
    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Business exception occurred: {}", e.message, e)
        
        return ResponseEntity
            .status(e.status)
            .body(ApiResponse.error(e.errorCode, e.message, e.status))
    }
    
    /**
     * 기본 예외 처리
     */
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Base exception occurred: {}", e.message, e)
        
        return ResponseEntity
            .status(e.status)
            .body(ApiResponse.error(e.errorCode, e.message, e.status))
    }
    
    /**
     * 유효성 검사 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Validation exception occurred: {}", e.message, e)
        
        val fieldErrors = e.bindingResult.fieldErrors
        val errorMessage = if (fieldErrors.isNotEmpty()) {
            fieldErrors.joinToString(", ") { fieldError: FieldError ->
                "${fieldError.field}: ${fieldError.defaultMessage}"
            }
        } else {
            "유효성 검사 실패"
        }
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error("VALIDATION_ERROR", errorMessage, HttpStatus.BAD_REQUEST.value()))
    }
    
    /**
     * 기타 예외 처리
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Unexpected exception occurred: {}", e.message, e)
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(
                "INTERNAL_SERVER_ERROR",
                "서버 내부 오류가 발생했습니다.",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            ))
    }
}
