package com.weproud.core.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeUtilsTest {

    @Test
    fun `getCurrentDate should return current date`() {
        val currentDate = LocalDate.now()
        val result = DateTimeUtils.getCurrentDate()
        
        // 날짜가 같은지 확인 (시간대 차이로 인해 정확히 같지 않을 수 있음)
        assertEquals(currentDate.year, result.year)
        assertEquals(currentDate.month, result.month)
        assertEquals(currentDate.dayOfMonth, result.dayOfMonth)
    }

    @Test
    fun `getCurrentDateTime should return current date time`() {
        val currentDateTime = LocalDateTime.now()
        val result = DateTimeUtils.getCurrentDateTime()
        
        // 날짜가 같은지 확인 (시간대 차이로 인해 정확히 같지 않을 수 있음)
        assertEquals(currentDateTime.year, result.year)
        assertEquals(currentDateTime.month, result.month)
        assertEquals(currentDateTime.dayOfMonth, result.dayOfMonth)
    }

    @Test
    fun `formatDate should format date correctly`() {
        val date = LocalDate.of(2023, 5, 15)
        val expected = "2023-05-15"
        val result = DateTimeUtils.formatDate(date)
        
        assertEquals(expected, result)
    }

    @Test
    fun `formatDateTime should format date time correctly`() {
        val dateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val expected = "2023-05-15 10:30:45"
        val result = DateTimeUtils.formatDateTime(dateTime)
        
        assertEquals(expected, result)
    }

    @Test
    fun `parseDate should parse date string correctly`() {
        val dateStr = "2023-05-15"
        val expected = LocalDate.of(2023, 5, 15)
        val result = DateTimeUtils.parseDate(dateStr)
        
        assertEquals(expected, result)
    }

    @Test
    fun `parseDateTime should parse date time string correctly`() {
        val dateTimeStr = "2023-05-15 10:30:45"
        val expected = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val result = DateTimeUtils.parseDateTime(dateTimeStr)
        
        assertEquals(expected, result)
    }
}
