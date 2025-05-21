package com.weproud.core.exception

/**
 * 리소스를 찾을 수 없는 예외
 */
class ResourceNotFoundException(
    errorCode: String,
    message: String,
    statusCode: Int = 404,
    data: Any? = null,
    cause: Throwable? = null
) : BaseException(errorCode, message, statusCode, data, cause)
