package com.weproud.app.api.presentation.controller

import com.weproud.app.api.domain.exception.DomainException
import com.weproud.app.api.domain.usecase.GetUserUseCase
import com.weproud.app.api.domain.usecase.LoginUserUseCase
import com.weproud.app.api.domain.usecase.RegisterUserUseCase
import com.weproud.app.api.domain.usecase.UpdateUserUseCase
import com.weproud.app.api.presentation.dto.*
import com.weproud.app.api.presentation.mapper.UserDtoMapper
import com.weproud.core.config.ApiResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * 사용자 컨트롤러
 */
@RestController
@RequestMapping("/api/users")
class UserController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val userDtoMapper: UserDtoMapper
) {
    
    /**
     * 사용자 등록
     */
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterUserRequest): ApiResponse<UserResponse> {
        val user = registerUserUseCase.execute(
            email = request.email,
            password = request.password,
            name = request.name
        )
        
        return ApiResponse.success(userDtoMapper.toResponse(user))
    }
    
    /**
     * 사용자 로그인
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginUserRequest): ApiResponse<LoginUserResponse> {
        val loginResult = loginUserUseCase.execute(
            email = request.email,
            password = request.password
        )
        
        return ApiResponse.success(userDtoMapper.toLoginResponse(loginResult))
    }
    
    /**
     * 현재 사용자 정보 조회
     */
    @GetMapping("/me")
    fun getCurrentUser(@AuthenticationPrincipal userId: Long): ApiResponse<UserResponse> {
        val user = getUserUseCase.execute(userId)
            ?: throw DomainException.userNotFound(userId)
        
        return ApiResponse.success(userDtoMapper.toResponse(user))
    }
    
    /**
     * 사용자 정보 수정
     */
    @PutMapping("/me")
    fun updateUser(
        @AuthenticationPrincipal userId: Long,
        @Valid @RequestBody request: UpdateUserRequest
    ): ApiResponse<UserResponse> {
        val updatedUser = updateUserUseCase.execute(
            id = userId,
            name = request.name,
            password = request.password
        )
        
        return ApiResponse.success(userDtoMapper.toResponse(updatedUser))
    }
    
    /**
     * ID로 사용자 조회
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ApiResponse<UserResponse> {
        val user = getUserUseCase.execute(id)
            ?: throw DomainException.userNotFound(id)
        
        return ApiResponse.success(userDtoMapper.toResponse(user))
    }
}
