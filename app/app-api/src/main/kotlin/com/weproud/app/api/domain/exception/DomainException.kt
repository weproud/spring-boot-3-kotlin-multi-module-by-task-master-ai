package com.weproud.app.api.domain.exception

import com.weproud.core.exception.BaseException

/**
 * 도메인 예외 클래스
 */
class DomainException(
    errorCode: String,
    message: String,
    status: Int = 400,
    data: Any? = null,
    cause: Throwable? = null
) : BaseException(errorCode, message, status, data, cause) {
    
    companion object {
        /**
         * 사용자를 찾을 수 없음
         */
        fun userNotFound(id: Long): DomainException {
            return DomainException(
                errorCode = "USER_NOT_FOUND",
                message = "사용자를 찾을 수 없습니다. (ID: $id)",
                status = 404
            )
        }
        
        /**
         * 이메일 중복
         */
        fun emailAlreadyExists(email: String): DomainException {
            return DomainException(
                errorCode = "EMAIL_ALREADY_EXISTS",
                message = "이미 사용 중인 이메일입니다. ($email)",
                status = 400
            )
        }
        
        /**
         * 인증 실패
         */
        fun authenticationFailed(): DomainException {
            return DomainException(
                errorCode = "AUTHENTICATION_FAILED",
                message = "이메일 또는 비밀번호가 일치하지 않습니다.",
                status = 401
            )
        }
    }
}
