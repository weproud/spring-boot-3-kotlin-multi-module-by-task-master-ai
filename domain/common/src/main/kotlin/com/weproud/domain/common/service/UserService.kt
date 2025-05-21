package com.weproud.domain.common.service

import com.weproud.domain.common.dto.UserProfileUpdateRequest
import com.weproud.domain.common.dto.UserRegistrationRequest
import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.model.User

/**
 * 사용자 관리 서비스 인터페이스
 */
interface UserService {
    /**
     * 사용자 등록
     *
     * @param request 사용자 등록 요청 정보
     * @return 등록된 사용자 정보
     */
    fun registerUser(request: UserRegistrationRequest): UserResponse

    /**
     * 사용자 ID로 사용자 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    fun getUserById(userId: Long): UserResponse

    /**
     * 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return 사용자 정보
     */
    fun getUserByEmail(email: String): UserResponse

    /**
     * 모든 사용자 조회
     *
     * @return 사용자 목록
     */
    fun getAllUsers(): List<UserResponse>

    /**
     * 사용자 프로필 업데이트
     *
     * @param userId 사용자 ID
     * @param request 프로필 업데이트 요청 정보
     * @return 업데이트된 사용자 정보
     */
    fun updateUserProfile(userId: Long, request: UserProfileUpdateRequest): UserResponse

    /**
     * 사용자 비활성화
     *
     * @param userId 사용자 ID
     * @return 비활성화된 사용자 정보
     */
    fun deactivateUser(userId: Long): UserResponse

    /**
     * 사용자 삭제
     *
     * @param userId 사용자 ID
     */
    fun deleteUser(userId: Long)

    /**
     * 이메일 중복 확인
     *
     * @param email 확인할 이메일
     * @return 중복 여부 (true: 중복, false: 중복 아님)
     */
    fun isEmailExists(email: String): Boolean
}
