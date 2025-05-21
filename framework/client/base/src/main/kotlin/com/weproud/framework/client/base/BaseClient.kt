package com.weproud.framework.client.base

import com.weproud.core.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

/**
 * 모든 HTTP 클라이언트의 기본 클래스
 */
abstract class BaseClient(
    protected val webClient: WebClient
) {
    /**
     * GET 요청 처리
     */
    protected fun <T> get(uri: String, responseType: Class<T>, headers: Map<String, String> = emptyMap()): T {
        return webClient.get()
            .uri(uri)
            .headers { httpHeaders -> headers.forEach { (key, value) -> httpHeaders.add(key, value) } }
            .retrieve()
            .bodyToMono(responseType)
            .onErrorResume { handleError(it) }
            .block() ?: throw BusinessException("CLIENT_ERROR", "응답 데이터가 없습니다.")
    }
    
    /**
     * POST 요청 처리
     */
    protected fun <T, R> post(uri: String, body: T, responseType: Class<R>, headers: Map<String, String> = emptyMap()): R {
        return webClient.post()
            .uri(uri)
            .headers { httpHeaders -> headers.forEach { (key, value) -> httpHeaders.add(key, value) } }
            .bodyValue(body)
            .retrieve()
            .bodyToMono(responseType)
            .onErrorResume { handleError(it) }
            .block() ?: throw BusinessException("CLIENT_ERROR", "응답 데이터가 없습니다.")
    }
    
    /**
     * 에러 처리
     */
    private fun <T> handleError(throwable: Throwable): Mono<T> {
        return when (throwable) {
            is WebClientResponseException -> {
                val errorMessage = throwable.responseBodyAsString
                val status = throwable.statusCode.value()
                
                Mono.error(
                    BusinessException(
                        errorCode = "CLIENT_ERROR_${status}",
                        message = errorMessage.ifEmpty { "외부 API 호출 중 오류가 발생했습니다." },
                        status = status
                    )
                )
            }
            else -> Mono.error(
                BusinessException(
                    errorCode = "CLIENT_ERROR",
                    message = throwable.message ?: "외부 API 호출 중 오류가 발생했습니다.",
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value()
                )
            )
        }
    }
}
