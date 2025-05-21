package com.weproud.core.exception

/**
 * 애플리케이션의 모든 예외의 기본 클래스
 */
abstract class BaseException(
    val errorCode: String,
    override val message: String,
    val statusCode: Int = 500,
    val data: Any? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)
