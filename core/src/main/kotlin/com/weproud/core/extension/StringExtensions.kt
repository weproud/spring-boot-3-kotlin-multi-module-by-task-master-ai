package com.weproud.core.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 문자열이 null이거나 빈 문자열인지 확인
 */
fun String?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()

/**
 * 문자열이 null이 아니고 빈 문자열이 아닌지 확인
 */
fun String?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()

/**
 * 문자열이 null이거나 빈 문자열이거나 공백 문자로만 이루어져 있는지 확인
 */
fun String?.isNullOrBlank(): Boolean = this == null || this.isBlank()

/**
 * 문자열이 null이 아니고 빈 문자열이 아니며 공백 문자로만 이루어져 있지 않은지 확인
 */
fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()

/**
 * 문자열을 LocalDate로 변환
 */
fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))

/**
 * 문자열을 LocalDateTime으로 변환
 */
fun String.toLocalDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))
