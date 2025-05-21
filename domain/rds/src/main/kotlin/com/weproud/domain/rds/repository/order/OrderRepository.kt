package com.weproud.domain.rds.repository.order

import com.weproud.domain.rds.entity.order.OrderEntity
import com.weproud.domain.rds.entity.order.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * 주문 리포지토리
 */
@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long> {
    
    /**
     * 사용자 ID로 주문 목록 조회
     */
    fun findByUserId(userId: Long, pageable: Pageable): Page<OrderEntity>
    
    /**
     * 주문 상태로 주문 목록 조회
     */
    fun findByOrderStatus(orderStatus: OrderStatus, pageable: Pageable): Page<OrderEntity>
    
    /**
     * 사용자 ID와 주문 상태로 주문 목록 조회
     */
    fun findByUserIdAndOrderStatus(userId: Long, orderStatus: OrderStatus, pageable: Pageable): Page<OrderEntity>
    
    /**
     * 기간 내 주문 목록 조회
     */
    fun findByCreatedAtBetween(startDate: LocalDateTime, endDate: LocalDateTime, pageable: Pageable): Page<OrderEntity>
    
    /**
     * 주문과 주문 항목 함께 조회
     */
    @Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.orderItems WHERE o.id = :orderId")
    fun findOrderWithItems(orderId: Long): OrderEntity?
}
