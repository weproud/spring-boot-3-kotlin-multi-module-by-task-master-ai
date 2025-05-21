package com.weproud.core.config

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * API 응답 객체
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null
) {
    companion object {
        /**
         * 성공 응답 생성
         */
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(true, data, null)

        /**
         * 성공 응답 생성 (데이터 없음)
         */
        fun success(): ApiResponse<Unit> = ApiResponse(true, Unit, null)

        /**
         * 에러 응답 생성
         */
        fun <T> error(errorCode: String, message: String, statusCode: Int = 400, data: Any? = null): ApiResponse<T> =
            ApiResponse(false, null, ErrorResponse(errorCode, message, statusCode, data))
    }
}

/**
 * 에러 응답 객체
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val errorCode: String,
    val message: String,
    val statusCode: Int = 400,
    val data: Any? = null
)
