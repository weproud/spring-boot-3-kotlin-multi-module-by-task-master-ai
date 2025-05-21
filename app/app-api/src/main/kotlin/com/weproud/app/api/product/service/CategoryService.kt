package com.weproud.app.api.product.service

import com.weproud.app.api.common.exception.BusinessException
import com.weproud.app.api.common.exception.ErrorCode
import com.weproud.app.api.product.dto.CategoryCreateRequest
import com.weproud.app.api.product.dto.CategoryResponse
import com.weproud.app.api.product.dto.CategoryUpdateRequest
import com.weproud.app.api.product.mapper.ProductMapper
import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.product.CategoryEntity
import com.weproud.domain.rds.repository.product.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 카테고리 서비스
 */
@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val productMapper: ProductMapper
) {
    
    /**
     * 카테고리 생성
     */
    @Transactional
    fun createCategory(request: CategoryCreateRequest): CategoryResponse {
        // 부모 카테고리 조회
        val parentCategory = request.parentId?.let { parentId ->
            categoryRepository.findById(parentId)
                .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        }
        
        // 카테고리 엔티티 생성
        val categoryEntity = CategoryEntity(
            name = request.name,
            description = request.description,
            parent = parentCategory
        )
        
        // 카테고리 저장
        val savedCategory = categoryRepository.save(categoryEntity)
        
        // 응답 변환 및 반환
        return productMapper.toCategoryResponse(savedCategory)
    }
    
    /**
     * 카테고리 조회
     */
    @Transactional(readOnly = true)
    fun getCategoryById(id: Long): CategoryResponse {
        // ID로 카테고리 조회
        val categoryEntity = categoryRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        
        // 응답 변환 및 반환
        return productMapper.toCategoryResponse(categoryEntity)
    }
    
    /**
     * 최상위 카테고리 목록 조회
     */
    @Transactional(readOnly = true)
    fun getRootCategories(): List<CategoryResponse> {
        // 최상위 카테고리 목록 조회
        val categories = categoryRepository.findByParentIsNullAndStatus(Status.ACTIVE)
        
        // 응답 변환 및 반환
        return categories.map { productMapper.toCategoryResponse(it) }
    }
    
    /**
     * 하위 카테고리 목록 조회
     */
    @Transactional(readOnly = true)
    fun getSubCategories(parentId: Long): List<CategoryResponse> {
        // 부모 카테고리 존재 여부 확인
        if (!categoryRepository.existsById(parentId)) {
            throw BusinessException(ErrorCode.CATEGORY_NOT_FOUND)
        }
        
        // 하위 카테고리 목록 조회
        val categories = categoryRepository.findByParentIdAndStatus(parentId, Status.ACTIVE)
        
        // 응답 변환 및 반환
        return categories.map { productMapper.toCategoryResponse(it) }
    }
    
    /**
     * 카테고리 수정
     */
    @Transactional
    fun updateCategory(id: Long, request: CategoryUpdateRequest): CategoryResponse {
        // ID로 카테고리 조회
        val categoryEntity = categoryRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        
        // 부모 카테고리 조회
        val parentCategory = request.parentId?.let { parentId ->
            // 자기 자신을 부모로 설정하는 것 방지
            if (parentId == id) {
                throw BusinessException(ErrorCode.INVALID_INPUT_VALUE, "카테고리는 자기 자신을 부모로 설정할 수 없습니다.")
            }
            
            categoryRepository.findById(parentId)
                .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        }
        
        // 카테고리 정보 수정
        if (request.name != null) {
            categoryEntity.name = request.name
        }
        
        if (request.description != null) {
            categoryEntity.description = request.description
        }
        
        if (parentCategory != null) {
            categoryEntity.parent = parentCategory
        }
        
        // 카테고리 저장
        val updatedCategory = categoryRepository.save(categoryEntity)
        
        // 응답 변환 및 반환
        return productMapper.toCategoryResponse(updatedCategory)
    }
    
    /**
     * 카테고리 삭제
     */
    @Transactional
    fun deleteCategory(id: Long) {
        // ID로 카테고리 조회
        val categoryEntity = categoryRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.CATEGORY_NOT_FOUND) }
        
        // 하위 카테고리 존재 여부 확인
        val subCategories = categoryRepository.findByParentIdAndStatus(id, Status.ACTIVE)
        if (subCategories.isNotEmpty()) {
            throw BusinessException(ErrorCode.INVALID_INPUT_VALUE, "하위 카테고리가 있는 카테고리는 삭제할 수 없습니다.")
        }
        
        // 카테고리 상태 변경 (소프트 삭제)
        categoryEntity.status = Status.DELETED
        
        // 카테고리 저장
        categoryRepository.save(categoryEntity)
    }
}
