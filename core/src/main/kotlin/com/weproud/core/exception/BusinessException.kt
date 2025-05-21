package com.weproud.core.exception

/**
 * 비즈니스 로직 관련 예외
 */
class BusinessException(
    errorCode: String,
    message: String,
    statusCode: Int = 400,
    data: Any? = null,
    cause: Throwable? = null
) : BaseException(errorCode, message, statusCode, data, cause)
