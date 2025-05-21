package com.weproud.app.api.common.exception

import com.weproud.app.api.common.dto.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

/**
 * 글로벌 예외 핸들러
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val log = LoggerFactory.getLogger(javaClass)
    
    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Business exception occurred: {}", e.message, e)
        
        return ResponseEntity
            .status(e.errorCode.status)
            .body(ApiResponse.error(e.errorCode.code, e.message, e.getStatusCode()))
    }
    
    /**
     * 유효성 검사 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Method argument validation exception occurred: {}", e.message, e)
        
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        val errorMessage = getFieldErrorMessage(e.bindingResult.fieldErrors)
        
        return ResponseEntity
            .status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorMessage, errorCode.status.value()))
    }
    
    /**
     * 바인딩 예외 처리
     */
    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Bind exception occurred: {}", e.message, e)
        
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        val errorMessage = getFieldErrorMessage(e.bindingResult.fieldErrors)
        
        return ResponseEntity
            .status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorMessage, errorCode.status.value()))
    }
    
    /**
     * 메서드 인자 타입 불일치 예외 처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Method argument type mismatch exception occurred: {}", e.message, e)
        
        val errorCode = ErrorCode.INVALID_TYPE_VALUE
        val errorMessage = "${e.name} 값의 타입이 올바르지 않습니다. (요청 값: ${e.value}, 필요한 타입: ${e.requiredType?.simpleName})"
        
        return ResponseEntity
            .status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorMessage, errorCode.status.value()))
    }
    
    /**
     * HTTP 메서드 지원하지 않음 예외 처리
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("HTTP method not supported exception occurred: {}", e.message, e)
        
        val errorCode = ErrorCode.METHOD_NOT_ALLOWED
        val errorMessage = "${e.method} 메서드는 지원하지 않습니다. 지원하는 메서드: ${e.supportedMethods?.joinToString(", ")}"
        
        return ResponseEntity
            .status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorMessage, errorCode.status.value()))
    }
    
    /**
     * 기타 예외 처리
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Unexpected exception occurred: {}", e.message, e)
        
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        
        return ResponseEntity
            .status(errorCode.status)
            .body(ApiResponse.error(errorCode.code, errorCode.message, errorCode.status.value()))
    }
    
    /**
     * 필드 에러 메시지 생성
     */
    private fun getFieldErrorMessage(fieldErrors: List<FieldError>): String {
        return if (fieldErrors.isNotEmpty()) {
            fieldErrors.joinToString(", ") { fieldError ->
                "${fieldError.field}: ${fieldError.defaultMessage}"
            }
        } else {
            "유효성 검사 실패"
        }
    }
}
