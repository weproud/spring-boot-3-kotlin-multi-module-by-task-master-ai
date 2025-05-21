package com.weproud.core.util

/**
 * 문자열 관련 유틸리티 클래스
 */
object StringUtils {
    /**
     * 문자열이 null이거나 빈 문자열인지 확인
     */
    fun isEmpty(str: String?): Boolean = str == null || str.isEmpty()

    /**
     * 문자열이 null이 아니고 빈 문자열이 아닌지 확인
     */
    fun isNotEmpty(str: String?): Boolean = !isEmpty(str)

    /**
     * 문자열이 null이거나 빈 문자열이거나 공백 문자로만 이루어져 있는지 확인
     */
    fun isBlank(str: String?): Boolean = str == null || str.isBlank()

    /**
     * 문자열이 null이 아니고 빈 문자열이 아니며 공백 문자로만 이루어져 있지 않은지 확인
     */
    fun isNotBlank(str: String?): Boolean = !isBlank(str)

    /**
     * 문자열이 null인 경우 빈 문자열을 반환, 그렇지 않으면 원래 문자열을 반환
     */
    fun nullToEmpty(str: String?): String = str ?: ""

    /**
     * 문자열이 null이거나 빈 문자열인 경우 기본값을 반환, 그렇지 않으면 원래 문자열을 반환
     */
    fun defaultIfEmpty(str: String?, defaultValue: String): String = if (isEmpty(str)) defaultValue else str!!

    /**
     * 문자열이 null이거나 빈 문자열이거나 공백 문자로만 이루어진 경우 기본값을 반환, 그렇지 않으면 원래 문자열을 반환
     */
    fun defaultIfBlank(str: String?, defaultValue: String): String = if (isBlank(str)) defaultValue else str!!
}
