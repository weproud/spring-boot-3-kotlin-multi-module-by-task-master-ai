package com.weproud.domain.rds.repository.order

import com.weproud.domain.rds.entity.order.Address
import com.weproud.domain.rds.entity.order.OrderEntity
import com.weproud.domain.rds.entity.order.OrderItemEntity
import com.weproud.domain.rds.entity.order.OrderStatus
import com.weproud.domain.rds.entity.product.ProductEntity
import com.weproud.domain.rds.entity.user.UserEntity
import com.weproud.domain.rds.repository.product.ProductRepository
import com.weproud.domain.rds.repository.user.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDateTime

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {
    
    @Autowired
    private lateinit var orderRepository: OrderRepository
    
    @Autowired
    private lateinit var userRepository: UserRepository
    
    @Autowired
    private lateinit var productRepository: ProductRepository
    
    @Autowired
    private lateinit var entityManager: TestEntityManager
    
    private lateinit var user: UserEntity
    private lateinit var product: ProductEntity
    
    @BeforeEach
    fun setup() {
        // 사용자 생성
        user = UserEntity(
            email = "test@example.com",
            name = "Test User",
            password = "encoded_password"
        )
        userRepository.save(user)
        
        // 상품 생성
        product = ProductEntity(
            name = "테스트 상품",
            description = "테스트 상품 설명",
            price = BigDecimal("10000"),
            stockQuantity = 100
        )
        productRepository.save(product)
        
        // 주문 생성
        val order1 = OrderEntity(
            user = user,
            totalAmount = BigDecimal("20000"),
            orderStatus = OrderStatus.PAID,
            paidAt = LocalDateTime.now().minusDays(1),
            shippingAddress = Address(
                zipcode = "12345",
                address1 = "서울시 강남구",
                address2 = "테스트 아파트 101호",
                recipientName = "수령인",
                recipientPhone = "010-1234-5678"
            )
        )
        
        val orderItem1 = OrderItemEntity(
            order = order1,
            product = product,
            quantity = 2,
            price = BigDecimal("10000")
        )
        
        order1.addOrderItem(orderItem1)
        
        val order2 = OrderEntity(
            user = user,
            totalAmount = BigDecimal("10000"),
            orderStatus = OrderStatus.PENDING,
            shippingAddress = Address(
                zipcode = "12345",
                address1 = "서울시 강남구",
                address2 = "테스트 아파트 101호",
                recipientName = "수령인",
                recipientPhone = "010-1234-5678"
            )
        )
        
        val orderItem2 = OrderItemEntity(
            order = order2,
            product = product,
            quantity = 1,
            price = BigDecimal("10000")
        )
        
        order2.addOrderItem(orderItem2)
        
        orderRepository.saveAll(listOf(order1, order2))
        entityManager.flush()
        entityManager.clear()
    }
    
    @Test
    fun `사용자 ID로 주문 목록 조회 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val orders = orderRepository.findByUserId(user.id!!, pageable)
        
        // then
        assertEquals(2, orders.totalElements)
    }
    
    @Test
    fun `주문 상태로 주문 목록 조회 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val paidOrders = orderRepository.findByOrderStatus(OrderStatus.PAID, pageable)
        val pendingOrders = orderRepository.findByOrderStatus(OrderStatus.PENDING, pageable)
        
        // then
        assertEquals(1, paidOrders.totalElements)
        assertEquals(1, pendingOrders.totalElements)
    }
    
    @Test
    fun `사용자 ID와 주문 상태로 주문 목록 조회 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val orders = orderRepository.findByUserIdAndOrderStatus(user.id!!, OrderStatus.PAID, pageable)
        
        // then
        assertEquals(1, orders.totalElements)
        assertEquals(OrderStatus.PAID, orders.content[0].orderStatus)
    }
    
    @Test
    fun `주문과 주문 항목 함께 조회 테스트`() {
        // given
        val orders = orderRepository.findAll()
        val orderId = orders[0].id!!
        
        // when
        val orderWithItems = orderRepository.findOrderWithItems(orderId)
        
        // then
        assertNotNull(orderWithItems)
        assertEquals(1, orderWithItems!!.orderItems.size)
    }
}
