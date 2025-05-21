package com.weproud.domain.common.service

import com.weproud.domain.common.dto.PasswordChangeRequest
import com.weproud.domain.common.dto.UserProfileUpdateRequest
import com.weproud.domain.common.dto.UserResponse

/**
 * 프로필 관리 서비스 인터페이스
 */
interface ProfileService {
    /**
     * 사용자 프로필 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 프로필 정보
     */
    fun getProfile(userId: Long): UserResponse

    /**
     * 사용자 프로필 업데이트
     *
     * @param userId 사용자 ID
     * @param request 프로필 업데이트 요청 정보
     * @return 업데이트된 사용자 프로필 정보
     */
    fun updateProfile(userId: Long, request: UserProfileUpdateRequest): UserResponse

    /**
     * 비밀번호 변경
     *
     * @param userId 사용자 ID
     * @param request 비밀번호 변경 요청 정보
     * @return 성공 여부
     */
    fun changePassword(userId: Long, request: PasswordChangeRequest): Boolean
}
