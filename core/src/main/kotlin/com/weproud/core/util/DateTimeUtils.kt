package com.weproud.core.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * 날짜 및 시간 관련 유틸리티 클래스
 */
object DateTimeUtils {
    private val DEFAULT_ZONE_ID = ZoneId.of("Asia/Seoul")
    private val DEFAULT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    
    /**
     * 현재 시간을 반환
     */
    fun now(): LocalDateTime = LocalDateTime.now(DEFAULT_ZONE_ID)
    
    /**
     * 현재 시간을 ZonedDateTime으로 반환
     */
    fun nowZoned(): ZonedDateTime = ZonedDateTime.now(DEFAULT_ZONE_ID)
    
    /**
     * LocalDateTime을 문자열로 변환
     */
    fun format(dateTime: LocalDateTime): String = dateTime.format(DEFAULT_DATETIME_FORMAT)
    
    /**
     * 문자열을 LocalDateTime으로 변환
     */
    fun parse(dateTimeStr: String): LocalDateTime = LocalDateTime.parse(dateTimeStr, DEFAULT_DATETIME_FORMAT)
}
