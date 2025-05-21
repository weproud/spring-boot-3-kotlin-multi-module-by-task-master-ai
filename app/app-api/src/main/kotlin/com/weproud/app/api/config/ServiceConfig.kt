package com.weproud.app.api.config

import com.weproud.domain.rds.config.RepositoryConfig
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * 서비스 설정 클래스
 */
@Configuration
@ComponentScan(basePackages = ["com.weproud.app.api.domain.service"])
@Import(RepositoryConfig::class)
class ServiceConfig
