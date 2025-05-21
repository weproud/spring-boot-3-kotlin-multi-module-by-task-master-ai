package com.weproud.app.api.presentation.controller

import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * 관리자 컨트롤러
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
    private val userService: UserService
) {

    /**
     * 모든 사용자 목록 조회
     *
     * @return 사용자 목록
     */
    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    /**
     * 사용자 ID로 사용자 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    @GetMapping("/users/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        val user = userService.getUserById(userId)
        return ResponseEntity.ok(user)
    }

    /**
     * 사용자 비활성화
     *
     * @param userId 사용자 ID
     * @return 비활성화된 사용자 정보
     */
    @PutMapping("/users/{userId}/deactivate")
    fun deactivateUser(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        val user = userService.deactivateUser(userId)
        return ResponseEntity.ok(user)
    }

    /**
     * 사용자 삭제
     *
     * @param userId 사용자 ID
     * @return 성공 메시지
     */
    @DeleteMapping("/users/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<Map<String, String>> {
        userService.deleteUser(userId)
        return ResponseEntity.ok(mapOf("message" to "사용자가 삭제되었습니다"))
    }
}
