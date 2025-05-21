package com.weproud.app.api.product.mapper

import com.weproud.app.api.product.dto.CategoryResponse
import com.weproud.app.api.product.dto.ProductImageResponse
import com.weproud.app.api.product.dto.ProductResponse
import com.weproud.domain.rds.entity.product.CategoryEntity
import com.weproud.domain.rds.entity.product.ProductEntity
import com.weproud.domain.rds.entity.product.ProductImageEntity
import org.springframework.stereotype.Component

/**
 * 상품 매퍼
 */
@Component
class ProductMapper {
    
    /**
     * ProductEntity를 ProductResponse로 변환
     */
    fun toProductResponse(productEntity: ProductEntity): ProductResponse {
        return ProductResponse(
            id = productEntity.id!!,
            name = productEntity.name,
            description = productEntity.description,
            price = productEntity.price,
            stockQuantity = productEntity.stockQuantity,
            category = productEntity.category?.let { toCategoryResponse(it) },
            images = productEntity.images.map { toProductImageResponse(it) },
            createdAt = productEntity.createdAt,
            updatedAt = productEntity.updatedAt
        )
    }
    
    /**
     * CategoryEntity를 CategoryResponse로 변환
     */
    fun toCategoryResponse(categoryEntity: CategoryEntity): CategoryResponse {
        return CategoryResponse(
            id = categoryEntity.id!!,
            name = categoryEntity.name,
            description = categoryEntity.description
        )
    }
    
    /**
     * ProductImageEntity를 ProductImageResponse로 변환
     */
    fun toProductImageResponse(productImageEntity: ProductImageEntity): ProductImageResponse {
        return ProductImageResponse(
            id = productImageEntity.id!!,
            url = productImageEntity.url,
            sortOrder = productImageEntity.sortOrder,
            alt = productImageEntity.alt
        )
    }
}
