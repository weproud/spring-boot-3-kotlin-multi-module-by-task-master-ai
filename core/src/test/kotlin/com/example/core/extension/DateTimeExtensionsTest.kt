package com.weproud.core.extension

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class DateTimeExtensionsTest {

    @Test
    fun `format should format LocalDate correctly`() {
        val date = LocalDate.of(2023, 5, 15)
        val expected = "2023-05-15"
        val result = date.format()
        
        assertEquals(expected, result)
    }

    @Test
    fun `format should format LocalDate with custom pattern correctly`() {
        val date = LocalDate.of(2023, 5, 15)
        val expected = "15/05/2023"
        val result = date.format("dd/MM/yyyy")
        
        assertEquals(expected, result)
    }

    @Test
    fun `format should format LocalDateTime correctly`() {
        val dateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val expected = "2023-05-15 10:30:45"
        val result = dateTime.format()
        
        assertEquals(expected, result)
    }

    @Test
    fun `format should format LocalDateTime with custom pattern correctly`() {
        val dateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val expected = "15/05/2023 10:30:45"
        val result = dateTime.format("dd/MM/yyyy HH:mm:ss")
        
        assertEquals(expected, result)
    }

    @Test
    fun `plusDays should add days to LocalDate correctly`() {
        val date = LocalDate.of(2023, 5, 15)
        val expected = LocalDate.of(2023, 5, 20)
        val result = date.plusDays(5)
        
        assertEquals(expected, result)
    }

    @Test
    fun `plusMonths should add months to LocalDate correctly`() {
        val date = LocalDate.of(2023, 5, 15)
        val expected = LocalDate.of(2023, 8, 15)
        val result = date.plusMonths(3)
        
        assertEquals(expected, result)
    }

    @Test
    fun `plusYears should add years to LocalDate correctly`() {
        val date = LocalDate.of(2023, 5, 15)
        val expected = LocalDate.of(2025, 5, 15)
        val result = date.plusYears(2)
        
        assertEquals(expected, result)
    }

    @Test
    fun `plusSeconds should add seconds to LocalDateTime correctly`() {
        val dateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val expected = LocalDateTime.of(2023, 5, 15, 10, 30, 50)
        val result = dateTime.plusSeconds(5)
        
        assertEquals(expected, result)
    }

    @Test
    fun `plusMinutes should add minutes to LocalDateTime correctly`() {
        val dateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val expected = LocalDateTime.of(2023, 5, 15, 10, 35, 45)
        val result = dateTime.plusMinutes(5)
        
        assertEquals(expected, result)
    }

    @Test
    fun `plusHours should add hours to LocalDateTime correctly`() {
        val dateTime = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val expected = LocalDateTime.of(2023, 5, 15, 15, 30, 45)
        val result = dateTime.plusHours(5)
        
        assertEquals(expected, result)
    }
}
