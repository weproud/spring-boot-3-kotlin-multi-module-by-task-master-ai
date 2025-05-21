package com.weproud.core.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StringUtilsTest {

    @Test
    fun `isEmpty should return true for null or empty string`() {
        assertTrue(StringUtils.isEmpty(null))
        assertTrue(StringUtils.isEmpty(""))
        assertFalse(StringUtils.isEmpty(" "))
        assertFalse(StringUtils.isEmpty("test"))
    }

    @Test
    fun `isNotEmpty should return true for non-null and non-empty string`() {
        assertFalse(StringUtils.isNotEmpty(null))
        assertFalse(StringUtils.isNotEmpty(""))
        assertTrue(StringUtils.isNotEmpty(" "))
        assertTrue(StringUtils.isNotEmpty("test"))
    }

    @Test
    fun `isBlank should return true for null, empty or blank string`() {
        assertTrue(StringUtils.isBlank(null))
        assertTrue(StringUtils.isBlank(""))
        assertTrue(StringUtils.isBlank(" "))
        assertTrue(StringUtils.isBlank("\t\n"))
        assertFalse(StringUtils.isBlank("test"))
    }

    @Test
    fun `isNotBlank should return true for non-null, non-empty and non-blank string`() {
        assertFalse(StringUtils.isNotBlank(null))
        assertFalse(StringUtils.isNotBlank(""))
        assertFalse(StringUtils.isNotBlank(" "))
        assertFalse(StringUtils.isNotBlank("\t\n"))
        assertTrue(StringUtils.isNotBlank("test"))
    }

    @Test
    fun `nullToEmpty should return empty string for null and original string otherwise`() {
        assertEquals("", StringUtils.nullToEmpty(null))
        assertEquals("", StringUtils.nullToEmpty(""))
        assertEquals(" ", StringUtils.nullToEmpty(" "))
        assertEquals("test", StringUtils.nullToEmpty("test"))
    }

    @Test
    fun `defaultIfEmpty should return default value for null or empty string and original string otherwise`() {
        assertEquals("default", StringUtils.defaultIfEmpty(null, "default"))
        assertEquals("default", StringUtils.defaultIfEmpty("", "default"))
        assertEquals(" ", StringUtils.defaultIfEmpty(" ", "default"))
        assertEquals("test", StringUtils.defaultIfEmpty("test", "default"))
    }

    @Test
    fun `defaultIfBlank should return default value for null, empty or blank string and original string otherwise`() {
        assertEquals("default", StringUtils.defaultIfBlank(null, "default"))
        assertEquals("default", StringUtils.defaultIfBlank("", "default"))
        assertEquals("default", StringUtils.defaultIfBlank(" ", "default"))
        assertEquals("default", StringUtils.defaultIfBlank("\t\n", "default"))
        assertEquals("test", StringUtils.defaultIfBlank("test", "default"))
    }
}
