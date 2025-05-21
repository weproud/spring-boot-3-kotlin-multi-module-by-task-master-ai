package com.weproud.app.api.product.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.weproud.app.api.common.dto.PageResponse
import com.weproud.app.api.product.dto.CategoryResponse
import com.weproud.app.api.product.dto.ProductCreateRequest
import com.weproud.app.api.product.dto.ProductResponse
import com.weproud.app.api.product.dto.ProductUpdateRequest
import com.weproud.app.api.product.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(ProductController::class)
class ProductControllerTest {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    
    @MockBean
    private lateinit var productService: ProductService
    
    @Test
    @WithMockUser
    fun `상품 생성 요청이 성공적으로 처리되어야 한다`() {
        // given
        val request = ProductCreateRequest(
            name = "Test Product",
            description = "Test Description",
            price = BigDecimal("10000"),
            stockQuantity = 100,
            categoryId = 1L
        )
        
        val response = ProductResponse(
            id = 1L,
            name = request.name,
            description = request.description,
            price = request.price,
            stockQuantity = request.stockQuantity,
            category = CategoryResponse(
                id = 1L,
                name = "Test Category",
                description = "Test Category Description"
            ),
            images = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        `when`(productService.createProduct(request)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value(request.name))
            .andExpect(jsonPath("$.data.description").value(request.description))
            .andExpect(jsonPath("$.data.price").value(10000))
            .andExpect(jsonPath("$.data.stockQuantity").value(100))
    }
    
    @Test
    @WithMockUser
    fun `상품 조회 요청이 성공적으로 처리되어야 한다`() {
        // given
        val productId = 1L
        
        val response = ProductResponse(
            id = productId,
            name = "Test Product",
            description = "Test Description",
            price = BigDecimal("10000"),
            stockQuantity = 100,
            category = CategoryResponse(
                id = 1L,
                name = "Test Category",
                description = "Test Category Description"
            ),
            images = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        `when`(productService.getProductById(productId)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            get("/api/products/{id}", productId)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(productId))
            .andExpect(jsonPath("$.data.name").value(response.name))
            .andExpect(jsonPath("$.data.description").value(response.description))
    }
    
    @Test
    @WithMockUser
    fun `상품 목록 조회 요청이 성공적으로 처리되어야 한다`() {
        // given
        val pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))
        
        val product1 = ProductResponse(
            id = 1L,
            name = "Test Product 1",
            description = "Test Description 1",
            price = BigDecimal("10000"),
            stockQuantity = 100,
            category = null,
            images = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val product2 = ProductResponse(
            id = 2L,
            name = "Test Product 2",
            description = "Test Description 2",
            price = BigDecimal("20000"),
            stockQuantity = 200,
            category = null,
            images = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        val pageResponse = PageResponse.of(
            content = listOf(product1, product2),
            page = 0,
            size = 10,
            totalElements = 2
        )
        
        `when`(productService.getProducts(pageable)).thenReturn(pageResponse)
        
        // when & then
        mockMvc.perform(
            get("/api/products")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id")
                .param("direction", "DESC")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.content.length()").value(2))
            .andExpect(jsonPath("$.data.content[0].id").value(1))
            .andExpect(jsonPath("$.data.content[1].id").value(2))
            .andExpect(jsonPath("$.data.totalElements").value(2))
    }
    
    @Test
    @WithMockUser
    fun `상품 수정 요청이 성공적으로 처리되어야 한다`() {
        // given
        val productId = 1L
        
        val request = ProductUpdateRequest(
            name = "Updated Product",
            description = "Updated Description",
            price = BigDecimal("15000"),
            stockQuantity = 150
        )
        
        val response = ProductResponse(
            id = productId,
            name = request.name!!,
            description = request.description!!,
            price = request.price!!,
            stockQuantity = request.stockQuantity!!,
            category = null,
            images = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        `when`(productService.updateProduct(productId, request)).thenReturn(response)
        
        // when & then
        mockMvc.perform(
            put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(productId))
            .andExpect(jsonPath("$.data.name").value(request.name))
            .andExpect(jsonPath("$.data.description").value(request.description))
            .andExpect(jsonPath("$.data.price").value(15000))
            .andExpect(jsonPath("$.data.stockQuantity").value(150))
    }
    
    @Test
    @WithMockUser
    fun `상품 삭제 요청이 성공적으로 처리되어야 한다`() {
        // given
        val productId = 1L
        
        // when & then
        mockMvc.perform(
            delete("/api/products/{id}", productId)
        )
            .andExpect(status().isNoContent)
            .andExpect(jsonPath("$.success").value(true))
        
        verify(productService).deleteProduct(productId)
    }
}
