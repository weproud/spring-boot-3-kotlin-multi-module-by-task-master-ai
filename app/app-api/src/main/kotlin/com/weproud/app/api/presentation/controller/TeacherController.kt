package com.weproud.app.api.presentation.controller

import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 교사 컨트롤러
 */
@RestController
@RequestMapping("/api/teacher")
@PreAuthorize("hasRole('TEACHER')")
class TeacherController(
    private val userService: UserService
) {

    /**
     * 모든 사용자 목록 조회
     * 교사는 사용자 목록을 조회할 수 있지만, 관리자 기능은 사용할 수 없음
     *
     * @return 사용자 목록
     */
    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }
}
