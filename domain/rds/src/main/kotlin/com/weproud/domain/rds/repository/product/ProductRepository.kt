package com.weproud.domain.rds.repository.product

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.product.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * 상품 리포지토리
 */
@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long> {
    
    /**
     * 카테고리 ID로 상품 목록 조회
     */
    fun findByCategoryId(categoryId: Long, pageable: Pageable): Page<ProductEntity>
    
    /**
     * 상품명으로 상품 검색
     */
    fun findByNameContainingAndStatus(name: String, status: Status, pageable: Pageable): Page<ProductEntity>
    
    /**
     * 가격 범위로 상품 검색
     */
    fun findByPriceBetweenAndStatus(minPrice: java.math.BigDecimal, maxPrice: java.math.BigDecimal, status: Status, pageable: Pageable): Page<ProductEntity>
    
    /**
     * 재고가 있는 상품 목록 조회
     */
    @Query("SELECT p FROM ProductEntity p WHERE p.stockQuantity > 0 AND p.status = :status")
    fun findByStockAvailableAndStatus(status: Status, pageable: Pageable): Page<ProductEntity>
}
