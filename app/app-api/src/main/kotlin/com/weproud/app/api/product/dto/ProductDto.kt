package com.weproud.app.api.product.dto

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * 상품 생성 요청 DTO
 */
data class ProductCreateRequest(
    @field:NotBlank(message = "상품명은 필수 입력값입니다.")
    @field:Size(min = 2, max = 100, message = "상품명은 2자 이상 100자 이하여야 합니다.")
    val name: String,
    
    @field:NotBlank(message = "상품 설명은 필수 입력값입니다.")
    @field:Size(max = 1000, message = "상품 설명은 1000자 이하여야 합니다.")
    val description: String,
    
    @field:NotNull(message = "가격은 필수 입력값입니다.")
    @field:Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    val price: BigDecimal,
    
    @field:NotNull(message = "재고 수량은 필수 입력값입니다.")
    @field:Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    val stockQuantity: Int,
    
    val categoryId: Long? = null
)

/**
 * 상품 수정 요청 DTO
 */
data class ProductUpdateRequest(
    @field:Size(min = 2, max = 100, message = "상품명은 2자 이상 100자 이하여야 합니다.")
    val name: String? = null,
    
    @field:Size(max = 1000, message = "상품 설명은 1000자 이하여야 합니다.")
    val description: String? = null,
    
    @field:Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    val price: BigDecimal? = null,
    
    @field:Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    val stockQuantity: Int? = null,
    
    val categoryId: Long? = null
)

/**
 * 상품 응답 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val category: CategoryResponse?,
    val images: List<ProductImageResponse> = emptyList(),
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

/**
 * 상품 이미지 응답 DTO
 */
data class ProductImageResponse(
    val id: Long,
    val url: String,
    val sortOrder: Int,
    val alt: String?
)

/**
 * 카테고리 응답 DTO
 */
data class CategoryResponse(
    val id: Long,
    val name: String,
    val description: String?
)

/**
 * 카테고리 생성 요청 DTO
 */
data class CategoryCreateRequest(
    @field:NotBlank(message = "카테고리명은 필수 입력값입니다.")
    @field:Size(min = 2, max = 50, message = "카테고리명은 2자 이상 50자 이하여야 합니다.")
    val name: String,
    
    val description: String? = null,
    
    val parentId: Long? = null
)

/**
 * 카테고리 수정 요청 DTO
 */
data class CategoryUpdateRequest(
    @field:Size(min = 2, max = 50, message = "카테고리명은 2자 이상 50자 이하여야 합니다.")
    val name: String? = null,
    
    val description: String? = null,
    
    val parentId: Long? = null
)
