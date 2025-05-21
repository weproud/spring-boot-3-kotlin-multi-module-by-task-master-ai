package com.weproud.app.api.config.security

import com.weproud.domain.rds.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * 사용자 상세 정보 서비스 구현 클래스
 */
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    /**
     * 사용자 이름(이메일)으로 사용자 상세 정보 로드
     *
     * @param username 사용자 이름(이메일)
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("사용자를 찾을 수 없습니다: $username") }

        // 사용자 역할에 기반한 권한 생성
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${userEntity.role.name}"))

        // Spring Security의 User 객체 반환
        return User.builder()
            .username(userEntity.email)
            .password(userEntity.password)
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(userEntity.status.name == "SUSPENDED")
            .credentialsExpired(false)
            .disabled(userEntity.status.name != "ACTIVE")
            .build()
    }
}
