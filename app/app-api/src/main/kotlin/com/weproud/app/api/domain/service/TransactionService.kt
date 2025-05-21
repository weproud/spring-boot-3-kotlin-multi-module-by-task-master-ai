package com.weproud.app.api.domain.service

import com.weproud.domain.common.dto.UserProfileUpdateRequest
import com.weproud.domain.common.dto.UserRegistrationRequest
import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

/**
 * 복잡한 트랜잭션 처리를 위한 서비스 클래스
 */
@Service
class TransactionService(
    private val userService: UserService,
    private val transactionTemplate: TransactionTemplate
) {
    private val logger = LoggerFactory.getLogger(TransactionService::class.java)

    /**
     * 사용자 등록 및 프로필 업데이트를 하나의 트랜잭션으로 처리
     * - 격리 수준: REPEATABLE_READ (동일한 데이터를 여러 번 읽어도 동일한 결과 보장)
     * - 전파 방식: REQUIRED (기존 트랜잭션이 있으면 참여, 없으면 새로 생성)
     */
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.REPEATABLE_READ,
        rollbackFor = [Exception::class]
    )
    fun registerUserWithProfile(
        registrationRequest: UserRegistrationRequest,
        profileUpdateRequest: UserProfileUpdateRequest
    ): UserResponse {
        logger.info("사용자 등록 및 프로필 업데이트 트랜잭션 시작")

        try {
            // 사용자 등록
            val userResponse = userService.registerUser(registrationRequest)

            // 프로필 업데이트
            val updatedUserResponse = userService.updateUserProfile(userResponse.id, profileUpdateRequest)

            logger.info("사용자 등록 및 프로필 업데이트 트랜잭션 완료")
            return updatedUserResponse
        } catch (e: Exception) {
            logger.error("사용자 등록 및 프로필 업데이트 트랜잭션 실패: {}", e.message)
            throw e
        }
    }

    /**
     * 프로그래밍 방식의 트랜잭션 처리 예시
     */
    fun registerUserProgrammatically(registrationRequest: UserRegistrationRequest): UserResponse? {
        return transactionTemplate.execute { status ->
            try {
                val userResponse = userService.registerUser(registrationRequest)
                userResponse
            } catch (e: Exception) {
                logger.error("프로그래밍 방식 트랜잭션 실패: {}", e.message)
                status.setRollbackOnly()
                null
            }
        }
    }

    /**
     * 중첩 트랜잭션 예시
     * - 전파 방식: REQUIRES_NEW (항상 새로운 트랜잭션 생성)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun nestedTransactionExample(userId: Long, request: UserProfileUpdateRequest): UserResponse {
        logger.info("중첩 트랜잭션 시작")
        return userService.updateUserProfile(userId, request)
    }

    /**
     * 읽기 전용 트랜잭션 예시
     */
    @Transactional(readOnly = true)
    fun readOnlyTransactionExample(userId: Long): UserResponse {
        logger.info("읽기 전용 트랜잭션 시작")
        return userService.getUserById(userId)
    }
}
