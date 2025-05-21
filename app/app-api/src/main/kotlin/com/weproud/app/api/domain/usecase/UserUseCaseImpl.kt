package com.weproud.app.api.domain.usecase

import com.weproud.app.api.domain.exception.DomainException
import com.weproud.app.api.domain.model.User
import com.weproud.app.api.domain.repository.UserRepository
import com.weproud.framework.provider.jwt.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * 사용자 등록 유스케이스 구현체
 */
@Service
class RegisterUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : RegisterUserUseCase {
    
    /**
     * 사용자 등록 실행
     */
    override fun execute(email: String, password: String, name: String): User {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(email)) {
            throw DomainException.emailAlreadyExists(email)
        }
        
        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(password)
        
        // 사용자 생성 및 저장
        val user = User(
            email = email,
            password = encodedPassword,
            name = name
        )
        
        return userRepository.save(user)
    }
}

/**
 * 사용자 로그인 유스케이스 구현체
 */
@Service
class LoginUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : LoginUserUseCase {
    
    /**
     * 로그인 실행
     */
    override fun execute(email: String, password: String): LoginUserUseCase.LoginResult {
        // 이메일로 사용자 조회
        val user = userRepository.findByEmail(email)
            ?: throw DomainException.authenticationFailed()
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.password)) {
            throw DomainException.authenticationFailed()
        }
        
        // JWT 토큰 생성
        val token = jwtProvider.generateToken(
            userId = user.id ?: throw IllegalStateException("사용자 ID가 없습니다."),
            roles = user.roles
        )
        
        return LoginUserUseCase.LoginResult(
            user = user,
            token = token
        )
    }
}

/**
 * 사용자 정보 조회 유스케이스 구현체
 */
@Service
class GetUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserUseCase {
    
    /**
     * ID로 사용자 조회
     */
    override fun execute(id: Long): User? {
        return userRepository.findById(id)
    }
    
    /**
     * 이메일로 사용자 조회
     */
    override fun executeByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}

/**
 * 사용자 정보 수정 유스케이스 구현체
 */
@Service
class UpdateUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UpdateUserUseCase {
    
    /**
     * 사용자 정보 수정
     */
    override fun execute(id: Long, name: String?, password: String?): User {
        // 사용자 조회
        val user = userRepository.findById(id)
            ?: throw DomainException.userNotFound(id)
        
        // 수정할 정보 설정
        val updatedUser = user.copy(
            name = name ?: user.name,
            password = if (password != null) passwordEncoder.encode(password) else user.password
        )
        
        // 저장 및 반환
        return userRepository.save(updatedUser)
    }
}
