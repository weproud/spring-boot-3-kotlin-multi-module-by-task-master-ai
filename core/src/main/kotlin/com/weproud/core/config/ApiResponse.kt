package com.weproud.core.config

/**
 * API 응답 표준 포맷
 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null
) {
    companion object {
        /**
         * 성공 응답 생성
         */
        fun <T> success(data: T? = null): ApiResponse<T> {
            return ApiResponse(
                success = true,
                data = data,
                error = null
            )
        }

        /**
         * 에러 응답 생성
         */
        fun <T> error(code: String, message: String, status: Int = 400): ApiResponse<T> {
            return ApiResponse(
                success = false,
                data = null,
                error = ErrorResponse(code, message, status)
            )
        }
    }
}

/**
 * 에러 응답 모델
 */
data class ErrorResponse(
    val code: String,
    val message: String,
    val status: Int
)
