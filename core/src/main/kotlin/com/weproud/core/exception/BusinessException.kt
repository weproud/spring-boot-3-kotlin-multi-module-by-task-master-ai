package com.weproud.core.exception

/**
 * 비즈니스 로직 관련 예외 클래스
 */
class BusinessException(
    errorCode: String,
    message: String,
    status: Int = 400,
    data: Any? = null,
    cause: Throwable? = null
) : BaseException(errorCode, message, status, data, cause)
