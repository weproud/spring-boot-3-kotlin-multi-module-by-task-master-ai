package com.weproud.domain.rds.entity.order

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.BaseEntity
import com.weproud.domain.rds.entity.user.UserEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * 주문 상태 열거형
 */
enum class OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    REFUNDED
}

/**
 * 주문 엔티티
 */
@Entity
@Table(name = "orders")
class OrderEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
    
    @Column(nullable = false)
    var totalAmount: BigDecimal,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var orderStatus: OrderStatus = OrderStatus.PENDING,
    
    @Column(nullable = true)
    var paidAt: LocalDateTime? = null,
    
    @Column(nullable = true)
    var shippedAt: LocalDateTime? = null,
    
    @Column(nullable = true)
    var deliveredAt: LocalDateTime? = null,
    
    @Column(nullable = true)
    var cancelledAt: LocalDateTime? = null,
    
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderItems: MutableList<OrderItemEntity> = mutableListOf(),
    
    @Embedded
    var shippingAddress: Address? = null
) : BaseEntity() {
    
    /**
     * 주문 항목 추가
     */
    fun addOrderItem(orderItem: OrderItemEntity) {
        orderItems.add(orderItem)
        orderItem.order = this
    }
    
    /**
     * 주문 항목 제거
     */
    fun removeOrderItem(orderItem: OrderItemEntity) {
        orderItems.remove(orderItem)
        orderItem.order = null
    }
    
    /**
     * 주문 취소
     */
    fun cancel() {
        if (orderStatus == OrderStatus.SHIPPED || orderStatus == OrderStatus.DELIVERED) {
            throw IllegalStateException("이미 배송 중이거나 배송 완료된 주문은 취소할 수 없습니다.")
        }
        
        orderStatus = OrderStatus.CANCELLED
        cancelledAt = LocalDateTime.now()
        
        // 재고 원복
        orderItems.forEach { it.cancel() }
    }
    
    /**
     * 주문 결제 완료
     */
    fun paid() {
        orderStatus = OrderStatus.PAID
        paidAt = LocalDateTime.now()
    }
    
    /**
     * 주문 배송 시작
     */
    fun ship() {
        if (orderStatus != OrderStatus.PAID) {
            throw IllegalStateException("결제 완료된 주문만 배송할 수 있습니다.")
        }
        
        orderStatus = OrderStatus.SHIPPED
        shippedAt = LocalDateTime.now()
    }
    
    /**
     * 주문 배송 완료
     */
    fun deliver() {
        if (orderStatus != OrderStatus.SHIPPED) {
            throw IllegalStateException("배송 중인 주문만 배송 완료 처리할 수 있습니다.")
        }
        
        orderStatus = OrderStatus.DELIVERED
        deliveredAt = LocalDateTime.now()
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as OrderEntity
        
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    
    override fun toString(): String {
        return "OrderEntity(id=$id, totalAmount=$totalAmount, orderStatus=$orderStatus, status=$status)"
    }
}
