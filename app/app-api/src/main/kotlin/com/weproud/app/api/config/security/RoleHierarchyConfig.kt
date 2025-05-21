package com.weproud.app.api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl

/**
 * 역할 계층 구조 설정 클래스
 */
@Configuration
class RoleHierarchyConfig {

    /**
     * 역할 계층 구조 빈 등록
     * ADMIN > TEACHER > USER 계층 구조 설정
     *
     * @return RoleHierarchy
     */
    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val roleHierarchy = RoleHierarchyImpl()
        val hierarchy = """
            ROLE_ADMIN > ROLE_TEACHER
            ROLE_TEACHER > ROLE_USER
        """.trimIndent()
        roleHierarchy.setHierarchy(hierarchy)
        return roleHierarchy
    }
}
