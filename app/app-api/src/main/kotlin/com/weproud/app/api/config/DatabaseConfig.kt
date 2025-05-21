package com.weproud.app.api.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

/**
 * 데이터베이스 설정 클래스
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.weproud.app.api.data.repository", "com.weproud.domain.rds.repository"])
class DatabaseConfig(private val env: Environment) {
    
    /**
     * 데이터소스 프로퍼티 설정
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }
    
    /**
     * 데이터소스 설정
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(dataSourceProperties: DataSourceProperties): DataSource {
        return dataSourceProperties.initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }
}
