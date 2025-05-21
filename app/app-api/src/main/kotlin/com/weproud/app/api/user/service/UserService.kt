package com.weproud.app.api.user.service

import com.weproud.app.api.common.exception.BusinessException
import com.weproud.app.api.common.exception.ErrorCode
import com.weproud.app.api.user.dto.UserLoginResponse
import com.weproud.app.api.user.dto.UserRegisterRequest
import com.weproud.app.api.user.dto.UserResponse
import com.weproud.app.api.user.dto.UserUpdateRequest
import com.weproud.app.api.user.mapper.UserMapper
import com.weproud.domain.rds.entity.user.UserEntity
import com.weproud.domain.rds.repository.user.UserRepository
import com.weproud.framework.provider.jwt.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 사용자 서비스
 */
@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val userMapper: UserMapper
) {
    
    /**
     * 사용자 등록
     */
    @Transactional
    fun register(request: UserRegisterRequest): UserResponse {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.email)) {
            throw BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS)
        }
        
        // 사용자 엔티티 생성
        val userEntity = UserEntity(
            email = request.email,
            name = request.name,
            password = passwordEncoder.encode(request.password)
        )
        
        // 사용자 저장
        val savedUser = userRepository.save(userEntity)
        
        // 응답 변환 및 반환
        return userMapper.toUserResponse(savedUser)
    }
    
    /**
     * 사용자 로그인
     */
    @Transactional(readOnly = true)
    fun login(email: String, password: String): UserLoginResponse {
        // 이메일로 사용자 조회
        val userEntity = userRepository.findByEmail(email)
            ?: throw BusinessException(ErrorCode.INVALID_CREDENTIALS)
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, userEntity.password)) {
            throw BusinessException(ErrorCode.INVALID_CREDENTIALS)
        }
        
        // JWT 토큰 생성
        val token = jwtProvider.generateToken(userEntity.id!!, userEntity.roles.toList())
        
        // 응답 변환 및 반환
        return userMapper.toUserLoginResponse(userEntity, token)
    }
    
    /**
     * 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    fun getUserById(id: Long): UserResponse {
        // ID로 사용자 조회
        val userEntity = userRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        
        // 응답 변환 및 반환
        return userMapper.toUserResponse(userEntity)
    }
    
    /**
     * 사용자 정보 수정
     */
    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): UserResponse {
        // ID로 사용자 조회
        val userEntity = userRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        
        // 이름 수정
        if (request.name != null) {
            userEntity.updateName(request.name)
        }
        
        // 비밀번호 수정
        if (request.password != null) {
            userEntity.updatePassword(passwordEncoder.encode(request.password))
        }
        
        // 사용자 저장
        val updatedUser = userRepository.save(userEntity)
        
        // 응답 변환 및 반환
        return userMapper.toUserResponse(updatedUser)
    }
}
