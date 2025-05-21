package com.weproud.app.admin.service

import com.weproud.app.admin.controller.dto.UserDetailResponse
import com.weproud.app.admin.controller.dto.UserStatusUpdateRequest
import com.weproud.domain.common.constant.Status
import com.weproud.domain.common.dto.PageRequest
import com.weproud.domain.common.dto.PageResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * 사용자 관리 서비스
 */
@Service
class UserManagementService {
    
    /**
     * 사용자 목록 조회
     */
    fun getUsers(pageRequest: PageRequest): PageResponse<UserDetailResponse> {
        // TODO: 실제 사용자 목록 조회 로직 구현
        // 임시 구현: 테스트용 사용자 목록 반환
        val users = listOf(
            UserDetailResponse(
                id = 1L,
                email = "user1@example.com",
                name = "사용자1",
                status = Status.ACTIVE,
                roles = listOf("USER"),
                createdAt = LocalDateTime.now().minusDays(10),
                updatedAt = LocalDateTime.now().minusDays(5)
            ),
            UserDetailResponse(
                id = 2L,
                email = "user2@example.com",
                name = "사용자2",
                status = Status.ACTIVE,
                roles = listOf("USER"),
                createdAt = LocalDateTime.now().minusDays(8),
                updatedAt = LocalDateTime.now().minusDays(3)
            )
        )
        
        return PageResponse.of(
            content = users,
            page = pageRequest.page,
            size = pageRequest.size,
            totalElements = 2
        )
    }
    
    /**
     * 사용자 상세 조회
     */
    fun getUser(userId: Long): UserDetailResponse {
        // TODO: 실제 사용자 조회 로직 구현
        // 임시 구현: 테스트용 사용자 정보 반환
        return UserDetailResponse(
            id = userId,
            email = "user$userId@example.com",
            name = "사용자$userId",
            status = Status.ACTIVE,
            roles = listOf("USER"),
            createdAt = LocalDateTime.now().minusDays(10),
            updatedAt = LocalDateTime.now().minusDays(5)
        )
    }
    
    /**
     * 사용자 상태 변경
     */
    fun updateUserStatus(userId: Long, request: UserStatusUpdateRequest) {
        // TODO: 실제 사용자 상태 변경 로직 구현
    }
    
    /**
     * 사용자 삭제
     */
    fun deleteUser(userId: Long) {
        // TODO: 실제 사용자 삭제 로직 구현
    }
}
