package com.weproud.domain.rds.repository

import com.weproud.domain.common.model.UserStatus
import com.weproud.domain.rds.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * 사용자 레포지토리
 */
@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    /**
     * 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return 사용자 엔티티 (Optional)
     */
    fun findByEmail(email: String): Optional<UserEntity>

    /**
     * 이메일 존재 여부 확인
     *
     * @param email 사용자 이메일
     * @return 존재 여부
     */
    fun existsByEmail(email: String): Boolean

    /**
     * 상태별 사용자 목록 조회
     *
     * @param status 사용자 상태
     * @return 사용자 엔티티 목록
     */
    fun findAllByStatus(status: UserStatus): List<UserEntity>
}
