package com.weproud.domain.rds.entity.product

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.BaseEntity
import jakarta.persistence.*

/**
 * 카테고리 엔티티
 */
@Entity
@Table(name = "categories")
class CategoryEntity(
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = true)
    var description: String? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: CategoryEntity? = null,
    
    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    val children: MutableList<CategoryEntity> = mutableListOf(),
    
    @OneToMany(mappedBy = "category")
    val products: MutableList<ProductEntity> = mutableListOf()
) : BaseEntity() {
    
    /**
     * 하위 카테고리 추가
     */
    fun addChild(child: CategoryEntity) {
        children.add(child)
        child.parent = this
    }
    
    /**
     * 하위 카테고리 제거
     */
    fun removeChild(child: CategoryEntity) {
        children.remove(child)
        child.parent = null
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as CategoryEntity
        
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    
    override fun toString(): String {
        return "CategoryEntity(id=$id, name='$name', status=$status)"
    }
}
