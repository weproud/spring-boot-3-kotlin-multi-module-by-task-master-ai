package com.weproud.domain.rds.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * JPA 설정 클래스
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.weproud.domain.rds.repository"])
class JpaConfig
