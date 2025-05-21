package com.weproud.app.api.product.service

import com.weproud.app.api.common.dto.PageResponse
import com.weproud.app.api.common.exception.BusinessException
import com.weproud.app.api.common.exception.ErrorCode
import com.weproud.app.api.product.dto.ProductCreateRequest
import com.weproud.app.api.product.dto.ProductResponse
import com.weproud.app.api.product.dto.ProductUpdateRequest
import com.weproud.app.api.product.mapper.ProductMapper
import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.product.ProductEntity
import com.weproud.domain.rds.repository.product.CategoryRepository
import com.weproud.domain.rds.repository.product.ProductRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * 상품 서비스
 */
@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val productMapper: ProductMapper
) {
    
    /**
     * 상품 생성
     */
    @Transactional
    fun createProduct(request: ProductCreateRequest): ProductResponse {
        // 카테고리 조회
        val category = request.categoryId?.let { categoryId ->
            categoryRepository.findById(categoryId)
                .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        }
        
        // 상품 엔티티 생성
        val productEntity = ProductEntity(
            name = request.name,
            description = request.description,
            price = request.price,
            stockQuantity = request.stockQuantity,
            category = category
        )
        
        // 상품 저장
        val savedProduct = productRepository.save(productEntity)
        
        // 응답 변환 및 반환
        return productMapper.toProductResponse(savedProduct)
    }
    
    /**
     * 상품 조회
     */
    @Transactional(readOnly = true)
    fun getProductById(id: Long): ProductResponse {
        // ID로 상품 조회
        val productEntity = productRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.PRODUCT_NOT_FOUND) }
        
        // 응답 변환 및 반환
        return productMapper.toProductResponse(productEntity)
    }
    
    /**
     * 상품 목록 조회
     */
    @Transactional(readOnly = true)
    fun getProducts(pageable: Pageable): PageResponse<ProductResponse> {
        // 상품 목록 조회
        val productPage = productRepository.findAll(pageable)
        
        // 응답 변환 및 반환
        val content = productPage.content.map { productMapper.toProductResponse(it) }
        return PageResponse.from(productPage.map { productMapper.toProductResponse(it) })
    }
    
    /**
     * 카테고리별 상품 목록 조회
     */
    @Transactional(readOnly = true)
    fun getProductsByCategory(categoryId: Long, pageable: Pageable): PageResponse<ProductResponse> {
        // 카테고리 존재 여부 확인
        if (!categoryRepository.existsById(categoryId)) {
            throw BusinessException(ErrorCode.CATEGORY_NOT_FOUND)
        }
        
        // 카테고리별 상품 목록 조회
        val productPage = productRepository.findByCategoryId(categoryId, pageable)
        
        // 응답 변환 및 반환
        return PageResponse.from(productPage.map { productMapper.toProductResponse(it) })
    }
    
    /**
     * 상품 검색
     */
    @Transactional(readOnly = true)
    fun searchProducts(
        name: String? = null,
        minPrice: BigDecimal? = null,
        maxPrice: BigDecimal? = null,
        inStock: Boolean? = null,
        pageable: Pageable
    ): PageResponse<ProductResponse> {
        // 검색 조건에 따라 상품 목록 조회
        val productPage = when {
            name != null -> {
                productRepository.findByNameContainingAndStatus(name, Status.ACTIVE, pageable)
            }
            minPrice != null && maxPrice != null -> {
                productRepository.findByPriceBetweenAndStatus(minPrice, maxPrice, Status.ACTIVE, pageable)
            }
            inStock == true -> {
                productRepository.findByStockAvailableAndStatus(Status.ACTIVE, pageable)
            }
            else -> {
                productRepository.findAll(pageable)
            }
        }
        
        // 응답 변환 및 반환
        return PageResponse.from(productPage.map { productMapper.toProductResponse(it) })
    }
    
    /**
     * 상품 수정
     */
    @Transactional
    fun updateProduct(id: Long, request: ProductUpdateRequest): ProductResponse {
        // ID로 상품 조회
        val productEntity = productRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.PRODUCT_NOT_FOUND) }
        
        // 카테고리 조회
        val category = request.categoryId?.let { categoryId ->
            categoryRepository.findById(categoryId)
                .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        }
        
        // 상품 정보 수정
        if (request.name != null) {
            productEntity.name = request.name
        }
        
        if (request.description != null) {
            productEntity.description = request.description
        }
        
        if (request.price != null) {
            productEntity.price = request.price
        }
        
        if (request.stockQuantity != null) {
            productEntity.stockQuantity = request.stockQuantity
        }
        
        if (category != null) {
            productEntity.category = category
        }
        
        // 상품 저장
        val updatedProduct = productRepository.save(productEntity)
        
        // 응답 변환 및 반환
        return productMapper.toProductResponse(updatedProduct)
    }
    
    /**
     * 상품 삭제
     */
    @Transactional
    fun deleteProduct(id: Long) {
        // ID로 상품 조회
        val productEntity = productRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.PRODUCT_NOT_FOUND) }
        
        // 상품 상태 변경 (소프트 삭제)
        productEntity.status = Status.DELETED
        
        // 상품 저장
        productRepository.save(productEntity)
    }
}
