package com.weproud.app.api.domain.service

import com.weproud.domain.common.dto.PasswordChangeRequest
import com.weproud.domain.common.dto.UserProfileUpdateRequest
import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.service.ProfileService
import com.weproud.domain.rds.entity.UserEntity
import com.weproud.domain.rds.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

/**
 * 프로필 관리 서비스 구현 클래스
 */
@Service
class ProfileServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : ProfileService {

    /**
     * 사용자 프로필 조회
     */
    @Transactional(readOnly = true)
    override fun getProfile(userId: Long): UserResponse {
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        return mapToUserResponse(userEntity)
    }

    /**
     * 사용자 프로필 업데이트
     */
    @Transactional
    override fun updateProfile(userId: Long, request: UserProfileUpdateRequest): UserResponse {
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        // 업데이트된 사용자 엔티티 생성
        val updatedUserEntity = UserEntity(
            id = userEntity.id,
            email = userEntity.email,
            password = userEntity.password,
            name = request.name ?: userEntity.name,
            nickname = request.nickname ?: userEntity.nickname,
            phoneNumber = request.phoneNumber ?: userEntity.phoneNumber,
            role = userEntity.role,
            status = userEntity.status,
            createdAt = userEntity.createdAt,
            updatedAt = ZonedDateTime.now()
        )

        // 사용자 저장
        val savedUser = userRepository.save(updatedUserEntity)

        // 응답 반환
        return mapToUserResponse(savedUser)
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    override fun changePassword(userId: Long, request: PasswordChangeRequest): Boolean {
        // 새 비밀번호와 확인 비밀번호 일치 여부 확인
        if (request.newPassword != request.confirmPassword) {
            throw IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다")
        }

        // 사용자 조회
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.currentPassword, userEntity.password)) {
            throw IllegalArgumentException("현재 비밀번호가 일치하지 않습니다")
        }

        // 새 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(request.newPassword)

        // 업데이트된 사용자 엔티티 생성
        val updatedUserEntity = UserEntity(
            id = userEntity.id,
            email = userEntity.email,
            password = encodedPassword,
            name = userEntity.name,
            nickname = userEntity.nickname,
            phoneNumber = userEntity.phoneNumber,
            role = userEntity.role,
            status = userEntity.status,
            createdAt = userEntity.createdAt,
            updatedAt = ZonedDateTime.now()
        )

        // 사용자 저장
        userRepository.save(updatedUserEntity)

        return true
    }

    /**
     * 사용자 엔티티를 응답 DTO로 변환
     */
    private fun mapToUserResponse(userEntity: UserEntity): UserResponse {
        return UserResponse(
            id = userEntity.id!!,
            email = userEntity.email,
            name = userEntity.name,
            nickname = userEntity.nickname,
            phoneNumber = userEntity.phoneNumber,
            role = userEntity.role,
            status = userEntity.status,
            createdAt = userEntity.createdAt!!,
            updatedAt = userEntity.updatedAt!!
        )
    }
}
