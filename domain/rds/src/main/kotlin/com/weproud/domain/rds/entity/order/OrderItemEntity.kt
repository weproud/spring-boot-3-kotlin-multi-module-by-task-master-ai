package com.weproud.domain.rds.entity.order

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.BaseEntity
import com.weproud.domain.rds.entity.product.ProductEntity
import jakarta.persistence.*
import java.math.BigDecimal

/**
 * 주문 항목 엔티티
 */
@Entity
@Table(name = "order_items")
class OrderItemEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: OrderEntity? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity,
    
    @Column(nullable = false)
    val quantity: Int,
    
    @Column(nullable = false)
    val price: BigDecimal
) : BaseEntity() {
    
    /**
     * 주문 항목 총액 계산
     */
    fun getTotalPrice(): BigDecimal {
        return price.multiply(BigDecimal(quantity))
    }
    
    /**
     * 주문 취소 시 재고 원복
     */
    fun cancel() {
        product.increaseStock(quantity)
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as OrderItemEntity
        
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    
    override fun toString(): String {
        return "OrderItemEntity(id=$id, quantity=$quantity, price=$price, status=$status)"
    }
}
