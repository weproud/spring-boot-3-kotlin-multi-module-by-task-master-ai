package com.weproud.app.api.common.exception

import org.springframework.http.HttpStatus

/**
 * 에러 코드 열거형
 */
enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus
) {
    // 공통 에러
    INVALID_INPUT_VALUE("C001", "유효하지 않은 입력값입니다.", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("C002", "지원하지 않는 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    ENTITY_NOT_FOUND("C003", "엔티티를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("C004", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TYPE_VALUE("C005", "유효하지 않은 타입입니다.", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("C006", "접근이 거부되었습니다.", HttpStatus.FORBIDDEN),
    
    // 사용자 관련 에러
    EMAIL_ALREADY_EXISTS("U001", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U002", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("U003", "이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    
    // 상품 관련 에러
    PRODUCT_NOT_FOUND("P001", "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("P002", "카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    OUT_OF_STOCK("P003", "상품의 재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    
    // 주문 관련 에러
    ORDER_NOT_FOUND("O001", "주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_CANCEL_ORDER("O002", "주문을 취소할 수 없습니다.", HttpStatus.BAD_REQUEST),
    
    // 인증 관련 에러
    UNAUTHORIZED("A001", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("A002", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("A003", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED);
}
