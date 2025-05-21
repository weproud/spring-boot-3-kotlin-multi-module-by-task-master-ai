package com.weproud.app.api.common.exception

/**
 * 비즈니스 예외 클래스
 */
class BusinessException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message,
    val data: Any? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    
    /**
     * HTTP 상태 코드 반환
     */
    fun getStatusCode(): Int {
        return errorCode.status.value()
    }
}
