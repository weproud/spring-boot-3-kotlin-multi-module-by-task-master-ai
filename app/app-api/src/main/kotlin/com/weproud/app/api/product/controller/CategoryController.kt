package com.weproud.app.api.product.controller

import com.weproud.app.api.common.dto.ApiResponse
import com.weproud.app.api.product.dto.CategoryCreateRequest
import com.weproud.app.api.product.dto.CategoryResponse
import com.weproud.app.api.product.dto.CategoryUpdateRequest
import com.weproud.app.api.product.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * 카테고리 컨트롤러
 */
@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {
    
    /**
     * 카테고리 생성
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@Valid @RequestBody request: CategoryCreateRequest): ApiResponse<CategoryResponse> {
        val category = categoryService.createCategory(request)
        return ApiResponse.success(category)
    }
    
    /**
     * 카테고리 조회
     */
    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ApiResponse<CategoryResponse> {
        val category = categoryService.getCategoryById(id)
        return ApiResponse.success(category)
    }
    
    /**
     * 최상위 카테고리 목록 조회
     */
    @GetMapping("/root")
    fun getRootCategories(): ApiResponse<List<CategoryResponse>> {
        val categories = categoryService.getRootCategories()
        return ApiResponse.success(categories)
    }
    
    /**
     * 하위 카테고리 목록 조회
     */
    @GetMapping("/{id}/subcategories")
    fun getSubCategories(@PathVariable id: Long): ApiResponse<List<CategoryResponse>> {
        val categories = categoryService.getSubCategories(id)
        return ApiResponse.success(categories)
    }
    
    /**
     * 카테고리 수정
     */
    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @Valid @RequestBody request: CategoryUpdateRequest
    ): ApiResponse<CategoryResponse> {
        val updatedCategory = categoryService.updateCategory(id, request)
        return ApiResponse.success(updatedCategory)
    }
    
    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(@PathVariable id: Long): ApiResponse<Nothing> {
        categoryService.deleteCategory(id)
        return ApiResponse.success()
    }
}
