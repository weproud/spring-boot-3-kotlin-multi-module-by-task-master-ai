package com.weproud.app.admin.controller

import com.weproud.app.admin.controller.dto.UserDetailResponse
import com.weproud.app.admin.controller.dto.UserStatusUpdateRequest
import com.weproud.app.admin.service.UserManagementService
import com.weproud.core.config.ApiResponse
import com.weproud.domain.common.dto.PageRequest
import com.weproud.domain.common.dto.PageResponse
import org.springframework.web.bind.annotation.*

/**
 * 사용자 관리 컨트롤러
 */
@RestController
@RequestMapping("/admin-api/users")
class UserManagementController(
    private val userManagementService: UserManagementService
) {
    
    /**
     * 사용자 목록 조회
     */
    @GetMapping
    fun getUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ApiResponse<PageResponse<UserDetailResponse>> {
        val pageRequest = PageRequest(page, size)
        val response = userManagementService.getUsers(pageRequest)
        return ApiResponse.success(response)
    }
    
    /**
     * 사용자 상세 조회
     */
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ApiResponse<UserDetailResponse> {
        val response = userManagementService.getUser(userId)
        return ApiResponse.success(response)
    }
    
    /**
     * 사용자 상태 변경
     */
    @PutMapping("/{userId}/status")
    fun updateUserStatus(
        @PathVariable userId: Long,
        @RequestBody request: UserStatusUpdateRequest
    ): ApiResponse<Unit> {
        userManagementService.updateUserStatus(userId, request)
        return ApiResponse.success()
    }
    
    /**
     * 사용자 삭제
     */
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long): ApiResponse<Unit> {
        userManagementService.deleteUser(userId)
        return ApiResponse.success()
    }
}
