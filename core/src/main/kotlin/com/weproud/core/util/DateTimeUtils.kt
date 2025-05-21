package com.weproud.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 날짜 및 시간 관련 유틸리티 클래스
 */
object DateTimeUtils {
    private val DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val DEFAULT_ZONE_ID = ZoneId.of("Asia/Seoul")

    /**
     * 현재 날짜를 반환
     */
    fun getCurrentDate(): LocalDate = LocalDate.now(DEFAULT_ZONE_ID)

    /**
     * 현재 날짜와 시간을 반환
     */
    fun getCurrentDateTime(): LocalDateTime = LocalDateTime.now(DEFAULT_ZONE_ID)

    /**
     * 날짜를 문자열로 변환
     */
    fun formatDate(date: LocalDate): String = date.format(DEFAULT_DATE_FORMATTER)

    /**
     * 날짜와 시간을 문자열로 변환
     */
    fun formatDateTime(dateTime: LocalDateTime): String = dateTime.format(DEFAULT_DATETIME_FORMATTER)

    /**
     * 문자열을 날짜로 변환
     */
    fun parseDate(dateStr: String): LocalDate = LocalDate.parse(dateStr, DEFAULT_DATE_FORMATTER)

    /**
     * 문자열을 날짜와 시간으로 변환
     */
    fun parseDateTime(dateTimeStr: String): LocalDateTime = LocalDateTime.parse(dateTimeStr, DEFAULT_DATETIME_FORMATTER)
}
