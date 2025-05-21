package com.weproud.app.api.product.controller

import com.weproud.app.api.common.dto.ApiResponse
import com.weproud.app.api.common.dto.PageResponse
import com.weproud.app.api.product.dto.ProductCreateRequest
import com.weproud.app.api.product.dto.ProductResponse
import com.weproud.app.api.product.dto.ProductUpdateRequest
import com.weproud.app.api.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * 상품 컨트롤러
 */
@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {
    
    /**
     * 상품 생성
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@Valid @RequestBody request: ProductCreateRequest): ApiResponse<ProductResponse> {
        val product = productService.createProduct(request)
        return ApiResponse.success(product)
    }
    
    /**
     * 상품 조회
     */
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ApiResponse<ProductResponse> {
        val product = productService.getProductById(id)
        return ApiResponse.success(product)
    }
    
    /**
     * 상품 목록 조회
     */
    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sort: String,
        @RequestParam(defaultValue = "DESC") direction: String
    ): ApiResponse<PageResponse<ProductResponse>> {
        val sortDirection = if (direction.equals("ASC", ignoreCase = true)) {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        
        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort))
        val products = productService.getProducts(pageable)
        
        return ApiResponse.success(products)
    }
    
    /**
     * 카테고리별 상품 목록 조회
     */
    @GetMapping("/category/{categoryId}")
    fun getProductsByCategory(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sort: String,
        @RequestParam(defaultValue = "DESC") direction: String
    ): ApiResponse<PageResponse<ProductResponse>> {
        val sortDirection = if (direction.equals("ASC", ignoreCase = true)) {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        
        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort))
        val products = productService.getProductsByCategory(categoryId, pageable)
        
        return ApiResponse.success(products)
    }
    
    /**
     * 상품 검색
     */
    @GetMapping("/search")
    fun searchProducts(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) minPrice: BigDecimal?,
        @RequestParam(required = false) maxPrice: BigDecimal?,
        @RequestParam(required = false) inStock: Boolean?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sort: String,
        @RequestParam(defaultValue = "DESC") direction: String
    ): ApiResponse<PageResponse<ProductResponse>> {
        val sortDirection = if (direction.equals("ASC", ignoreCase = true)) {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        
        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort))
        val products = productService.searchProducts(name, minPrice, maxPrice, inStock, pageable)
        
        return ApiResponse.success(products)
    }
    
    /**
     * 상품 수정
     */
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Valid @RequestBody request: ProductUpdateRequest
    ): ApiResponse<ProductResponse> {
        val updatedProduct = productService.updateProduct(id, request)
        return ApiResponse.success(updatedProduct)
    }
    
    /**
     * 상품 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable id: Long): ApiResponse<Nothing> {
        productService.deleteProduct(id)
        return ApiResponse.success()
    }
}
