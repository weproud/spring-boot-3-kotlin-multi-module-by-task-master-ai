package com.weproud.core.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ApiResponseTest {

    @Test
    fun `success with data should create successful response with data`() {
        val data = "test data"
        val response = ApiResponse.success(data)
        
        assertEquals(true, response.success)
        assertEquals(data, response.data)
        assertNull(response.error)
    }

    @Test
    fun `success without data should create successful response without data`() {
        val response = ApiResponse.success()
        
        assertEquals(true, response.success)
        assertEquals(Unit, response.data)
        assertNull(response.error)
    }

    @Test
    fun `error should create error response with error details`() {
        val errorCode = "E001"
        val message = "Error message"
        val statusCode = 400
        val errorData = mapOf("field" to "username")
        
        val response = ApiResponse.error<String>(errorCode, message, statusCode, errorData)
        
        assertEquals(false, response.success)
        assertNull(response.data)
        assertEquals(errorCode, response.error?.errorCode)
        assertEquals(message, response.error?.message)
        assertEquals(statusCode, response.error?.statusCode)
        assertEquals(errorData, response.error?.data)
    }

    @Test
    fun `error without data should create error response without error data`() {
        val errorCode = "E001"
        val message = "Error message"
        val statusCode = 400
        
        val response = ApiResponse.error<String>(errorCode, message, statusCode)
        
        assertEquals(false, response.success)
        assertNull(response.data)
        assertEquals(errorCode, response.error?.errorCode)
        assertEquals(message, response.error?.message)
        assertEquals(statusCode, response.error?.statusCode)
        assertNull(response.error?.data)
    }
}
