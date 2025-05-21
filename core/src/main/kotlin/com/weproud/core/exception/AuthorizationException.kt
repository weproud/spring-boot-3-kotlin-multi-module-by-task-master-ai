package com.weproud.core.exception

/**
 * 권한 관련 예외
 */
class AuthorizationException(
    errorCode: String,
    message: String,
    statusCode: Int = 403,
    data: Any? = null,
    cause: Throwable? = null
) : BaseException(errorCode, message, statusCode, data, cause)
