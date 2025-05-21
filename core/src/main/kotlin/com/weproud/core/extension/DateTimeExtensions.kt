package com.weproud.core.extension

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * LocalDate를 문자열로 변환
 */
fun LocalDate.format(pattern: String = "yyyy-MM-dd"): String =
    this.format(DateTimeFormatter.ofPattern(pattern))

/**
 * LocalDateTime을 문자열로 변환
 */
fun LocalDateTime.format(pattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    this.format(DateTimeFormatter.ofPattern(pattern))

/**
 * LocalDate에 일수를 더함
 */
fun LocalDate.plusDays(days: Long): LocalDate = this.plusDays(days)

/**
 * LocalDate에 월수를 더함
 */
fun LocalDate.plusMonths(months: Long): LocalDate = this.plusMonths(months)

/**
 * LocalDate에 연수를 더함
 */
fun LocalDate.plusYears(years: Long): LocalDate = this.plusYears(years)

/**
 * LocalDateTime에 초를 더함
 */
fun LocalDateTime.plusSeconds(seconds: Long): LocalDateTime = this.plusSeconds(seconds)

/**
 * LocalDateTime에 분을 더함
 */
fun LocalDateTime.plusMinutes(minutes: Long): LocalDateTime = this.plusMinutes(minutes)

/**
 * LocalDateTime에 시간을 더함
 */
fun LocalDateTime.plusHours(hours: Long): LocalDateTime = this.plusHours(hours)
