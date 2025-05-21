package com.weproud.domain.rds.entity.product

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.BaseEntity
import jakarta.persistence.*

/**
 * 상품 이미지 엔티티
 */
@Entity
@Table(name = "product_images")
class ProductImageEntity(
    @Column(nullable = false)
    var url: String,
    
    @Column(nullable = false)
    var sortOrder: Int = 0,
    
    @Column(nullable = true)
    var alt: String? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: ProductEntity? = null
) : BaseEntity() {
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as ProductImageEntity
        
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    
    override fun toString(): String {
        return "ProductImageEntity(id=$id, url='$url', sortOrder=$sortOrder, status=$status)"
    }
}
