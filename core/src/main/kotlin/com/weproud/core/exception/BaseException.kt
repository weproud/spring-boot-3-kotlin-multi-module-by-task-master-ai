package com.weproud.core.exception

/**
 * 애플리케이션의 기본 예외 클래스
 * 모든 비즈니스 예외는 이 클래스를 상속받아야 함
 */
abstract class BaseException(
    val errorCode: String,
    override val message: String,
    val status: Int,
    val data: Any? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)
