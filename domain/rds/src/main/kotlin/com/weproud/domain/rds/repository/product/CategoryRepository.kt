package com.weproud.domain.rds.repository.product

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.product.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * 카테고리 리포지토리
 */
@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
    
    /**
     * 이름으로 카테고리 조회
     */
    fun findByName(name: String): CategoryEntity?
    
    /**
     * 상위 카테고리가 없는 카테고리 목록 조회 (최상위 카테고리)
     */
    fun findByParentIsNullAndStatus(status: Status): List<CategoryEntity>
    
    /**
     * 상위 카테고리 ID로 하위 카테고리 목록 조회
     */
    fun findByParentIdAndStatus(parentId: Long, status: Status): List<CategoryEntity>
    
    /**
     * 카테고리와 모든 하위 카테고리 조회
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.id = :categoryId OR c.parent.id = :categoryId")
    fun findCategoryWithChildren(categoryId: Long): List<CategoryEntity>
}
