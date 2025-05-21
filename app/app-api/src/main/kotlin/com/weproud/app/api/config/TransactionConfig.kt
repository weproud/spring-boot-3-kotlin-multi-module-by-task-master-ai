package com.weproud.app.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import jakarta.persistence.EntityManagerFactory

/**
 * 트랜잭션 관리 설정 클래스
 */
@Configuration
@EnableTransactionManagement
class TransactionConfig {

    /**
     * JPA 트랜잭션 매니저 빈 등록
     *
     * @param entityManagerFactory 엔티티 매니저 팩토리
     * @return 트랜잭션 매니저
     */
    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory
        return transactionManager
    }
}
