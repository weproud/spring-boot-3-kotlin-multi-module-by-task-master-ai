package com.weproud.domain.rds.entity.product

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal

/**
 * 상품 엔티티
 */
@Entity
@Table(name = "products")
class ProductEntity(
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = false, length = 1000)
    var description: String,
    
    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal,
    
    @Column(name = "stock_quantity", nullable = false)
    var stockQuantity: Int,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: CategoryEntity? = null,
    
    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val images: MutableList<ProductImageEntity> = mutableListOf()
) : BaseEntity() {
    
    /**
     * 상품 이미지 추가
     */
    fun addImage(image: ProductImageEntity) {
        images.add(image)
        image.product = this
    }
    
    /**
     * 상품 이미지 제거
     */
    fun removeImage(image: ProductImageEntity) {
        images.remove(image)
        image.product = null
    }
    
    /**
     * 재고 감소
     */
    fun decreaseStock(quantity: Int) {
        val restStock = stockQuantity - quantity
        if (restStock < 0) {
            throw IllegalStateException("재고가 부족합니다.")
        }
        stockQuantity = restStock
    }
    
    /**
     * 재고 증가
     */
    fun increaseStock(quantity: Int) {
        stockQuantity += quantity
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as ProductEntity
        
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    
    override fun toString(): String {
        return "ProductEntity(id=$id, name='$name', price=$price, stockQuantity=$stockQuantity, status=$status)"
    }
}
