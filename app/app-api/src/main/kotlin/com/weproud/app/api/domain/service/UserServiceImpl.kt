package com.weproud.app.api.domain.service

import com.weproud.domain.common.dto.UserProfileUpdateRequest
import com.weproud.domain.common.dto.UserRegistrationRequest
import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.model.UserRole
import com.weproud.domain.common.model.UserStatus
import com.weproud.domain.common.service.UserService
import com.weproud.domain.rds.entity.UserEntity
import com.weproud.domain.rds.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

/**
 * 사용자 서비스 구현 클래스
 */
@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    /**
     * 사용자 등록
     */
    @Transactional
    override fun registerUser(request: UserRegistrationRequest): UserResponse {
        // 이메일 중복 확인
        if (isEmailExists(request.email)) {
            throw IllegalArgumentException("이미 등록된 이메일입니다: ${request.email}")
        }

        // 사용자 엔티티 생성
        val userEntity = UserEntity(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            nickname = request.nickname,
            phoneNumber = request.phoneNumber,
            role = UserRole.USER,
            status = UserStatus.ACTIVE,
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )

        // 사용자 저장
        val savedUser = userRepository.save(userEntity)

        // 응답 반환
        return mapToUserResponse(savedUser)
    }

    /**
     * 사용자 ID로 사용자 조회
     */
    @Transactional(readOnly = true)
    override fun getUserById(userId: Long): UserResponse {
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        return mapToUserResponse(userEntity)
    }

    /**
     * 이메일로 사용자 조회
     */
    @Transactional(readOnly = true)
    override fun getUserByEmail(email: String): UserResponse {
        val userEntity = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $email") }

        return mapToUserResponse(userEntity)
    }

    /**
     * 모든 사용자 조회
     */
    @Transactional(readOnly = true)
    override fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { mapToUserResponse(it) }
    }

    /**
     * 사용자 프로필 업데이트
     */
    @Transactional
    override fun updateUserProfile(userId: Long, request: UserProfileUpdateRequest): UserResponse {
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
     * 사용자 비활성화
     */
    @Transactional
    override fun deactivateUser(userId: Long): UserResponse {
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        // 비활성화된 사용자 엔티티 생성
        val deactivatedUserEntity = UserEntity(
            id = userEntity.id,
            email = userEntity.email,
            password = userEntity.password,
            name = userEntity.name,
            nickname = userEntity.nickname,
            phoneNumber = userEntity.phoneNumber,
            role = userEntity.role,
            status = UserStatus.INACTIVE,
            createdAt = userEntity.createdAt,
            updatedAt = ZonedDateTime.now()
        )

        // 사용자 저장
        val savedUser = userRepository.save(deactivatedUserEntity)

        // 응답 반환
        return mapToUserResponse(savedUser)
    }

    /**
     * 사용자 삭제
     */
    @Transactional
    override fun deleteUser(userId: Long) {
        val userEntity = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다: $userId") }

        // 삭제 상태로 변경
        val deletedUserEntity = UserEntity(
            id = userEntity.id,
            email = userEntity.email,
            password = userEntity.password,
            name = userEntity.name,
            nickname = userEntity.nickname,
            phoneNumber = userEntity.phoneNumber,
            role = userEntity.role,
            status = UserStatus.DELETED,
            createdAt = userEntity.createdAt,
            updatedAt = ZonedDateTime.now()
        )

        // 사용자 저장
        userRepository.save(deletedUserEntity)
    }

    /**
     * 이메일 중복 확인
     */
    @Transactional(readOnly = true)
    override fun isEmailExists(email: String): Boolean {
        return userRepository.existsByEmail(email)
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
