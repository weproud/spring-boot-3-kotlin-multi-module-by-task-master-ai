package com.weproud.domain.rds.repository.product

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.product.CategoryEntity
import com.weproud.domain.rds.entity.product.ProductEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    
    @Autowired
    private lateinit var productRepository: ProductRepository
    
    @Autowired
    private lateinit var categoryRepository: CategoryRepository
    
    @Autowired
    private lateinit var entityManager: TestEntityManager
    
    private lateinit var category: CategoryEntity
    
    @BeforeEach
    fun setup() {
        // 카테고리 생성
        category = CategoryEntity(
            name = "전자제품",
            description = "전자제품 카테고리"
        )
        categoryRepository.save(category)
        
        // 상품 생성
        val product1 = ProductEntity(
            name = "스마트폰",
            description = "최신 스마트폰",
            price = BigDecimal("1000000"),
            stockQuantity = 10,
            category = category
        )
        
        val product2 = ProductEntity(
            name = "노트북",
            description = "고성능 노트북",
            price = BigDecimal("2000000"),
            stockQuantity = 5,
            category = category
        )
        
        val product3 = ProductEntity(
            name = "태블릿",
            description = "태블릿 PC",
            price = BigDecimal("500000"),
            stockQuantity = 0,
            category = category
        )
        
        productRepository.saveAll(listOf(product1, product2, product3))
        entityManager.flush()
        entityManager.clear()
    }
    
    @Test
    fun `카테고리 ID로 상품 목록 조회 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val products = productRepository.findByCategoryId(category.id!!, pageable)
        
        // then
        assertEquals(3, products.totalElements)
    }
    
    @Test
    fun `상품명으로 상품 검색 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val products = productRepository.findByNameContainingAndStatus("스마트", Status.ACTIVE, pageable)
        
        // then
        assertEquals(1, products.totalElements)
        assertEquals("스마트폰", products.content[0].name)
    }
    
    @Test
    fun `가격 범위로 상품 검색 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val products = productRepository.findByPriceBetweenAndStatus(
            BigDecimal("500000"),
            BigDecimal("1500000"),
            Status.ACTIVE,
            pageable
        )
        
        // then
        assertEquals(2, products.totalElements)
    }
    
    @Test
    fun `재고가 있는 상품 목록 조회 테스트`() {
        // when
        val pageable = PageRequest.of(0, 10)
        val products = productRepository.findByStockAvailableAndStatus(Status.ACTIVE, pageable)
        
        // then
        assertEquals(2, products.totalElements)
    }
}
