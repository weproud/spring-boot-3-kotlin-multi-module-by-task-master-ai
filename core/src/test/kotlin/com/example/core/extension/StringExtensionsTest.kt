package com.weproud.core.extension

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class StringExtensionsTest {

    @Test
    fun `isNullOrEmpty should return true for null or empty string`() {
        val nullString: String? = null
        val emptyString = ""
        val blankString = " "
        val nonEmptyString = "test"
        
        assertTrue(nullString.isNullOrEmpty())
        assertTrue(emptyString.isNullOrEmpty())
        assertFalse(blankString.isNullOrEmpty())
        assertFalse(nonEmptyString.isNullOrEmpty())
    }

    @Test
    fun `isNotNullOrEmpty should return true for non-null and non-empty string`() {
        val nullString: String? = null
        val emptyString = ""
        val blankString = " "
        val nonEmptyString = "test"
        
        assertFalse(nullString.isNotNullOrEmpty())
        assertFalse(emptyString.isNotNullOrEmpty())
        assertTrue(blankString.isNotNullOrEmpty())
        assertTrue(nonEmptyString.isNotNullOrEmpty())
    }

    @Test
    fun `isNullOrBlank should return true for null, empty or blank string`() {
        val nullString: String? = null
        val emptyString = ""
        val blankString = " "
        val tabNewlineString = "\t\n"
        val nonEmptyString = "test"
        
        assertTrue(nullString.isNullOrBlank())
        assertTrue(emptyString.isNullOrBlank())
        assertTrue(blankString.isNullOrBlank())
        assertTrue(tabNewlineString.isNullOrBlank())
        assertFalse(nonEmptyString.isNullOrBlank())
    }

    @Test
    fun `isNotNullOrBlank should return true for non-null, non-empty and non-blank string`() {
        val nullString: String? = null
        val emptyString = ""
        val blankString = " "
        val tabNewlineString = "\t\n"
        val nonEmptyString = "test"
        
        assertFalse(nullString.isNotNullOrBlank())
        assertFalse(emptyString.isNotNullOrBlank())
        assertFalse(blankString.isNotNullOrBlank())
        assertFalse(tabNewlineString.isNotNullOrBlank())
        assertTrue(nonEmptyString.isNotNullOrBlank())
    }

    @Test
    fun `toLocalDate should convert string to LocalDate`() {
        val dateStr = "2023-05-15"
        val expected = LocalDate.of(2023, 5, 15)
        val result = dateStr.toLocalDate()
        
        assertEquals(expected, result)
    }

    @Test
    fun `toLocalDateTime should convert string to LocalDateTime`() {
        val dateTimeStr = "2023-05-15 10:30:45"
        val expected = LocalDateTime.of(2023, 5, 15, 10, 30, 45)
        val result = dateTimeStr.toLocalDateTime()
        
        assertEquals(expected, result)
    }
}
