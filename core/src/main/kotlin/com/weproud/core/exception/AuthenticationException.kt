package com.weproud.core.exception

/**
 * 인증 관련 예외
 */
class AuthenticationException(
    errorCode: String,
    message: String,
    statusCode: Int = 401,
    data: Any? = null,
    cause: Throwable? = null
) : BaseException(errorCode, message, statusCode, data, cause)
