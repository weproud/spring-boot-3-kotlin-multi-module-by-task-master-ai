package com.weproud.app.api.user.controller

import com.weproud.app.api.common.dto.ApiResponse
import com.weproud.app.api.user.dto.UserLoginRequest
import com.weproud.app.api.user.dto.UserLoginResponse
import com.weproud.app.api.user.dto.UserRegisterRequest
import com.weproud.app.api.user.dto.UserResponse
import com.weproud.app.api.user.dto.UserUpdateRequest
import com.weproud.app.api.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * 사용자 컨트롤러
 */
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    
    /**
     * 사용자 등록
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: UserRegisterRequest): ApiResponse<UserResponse> {
        val user = userService.register(request)
        return ApiResponse.success(user)
    }
    
    /**
     * 사용자 로그인
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: UserLoginRequest): ApiResponse<UserLoginResponse> {
        val loginResponse = userService.login(request.email, request.password)
        return ApiResponse.success(loginResponse)
    }
    
    /**
     * 현재 사용자 정보 조회
     */
    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userId: Long): ApiResponse<UserResponse> {
        val user = userService.getUserById(userId)
        return ApiResponse.success(user)
    }
    
    /**
     * 사용자 정보 수정
     */
    @PutMapping("/me")
    fun updateUser(
        @AuthenticationPrincipal userId: Long,
        @Valid @RequestBody request: UserUpdateRequest
    ): ApiResponse<UserResponse> {
        val updatedUser = userService.updateUser(userId, request)
        return ApiResponse.success(updatedUser)
    }
    
    /**
     * 사용자 정보 조회 (ID로)
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ApiResponse<UserResponse> {
        val user = userService.getUserById(id)
        return ApiResponse.success(user)
    }
}
