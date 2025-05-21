package com.weproud.app.api.presentation.controller

import com.weproud.domain.common.dto.UserLoginRequest
import com.weproud.domain.common.dto.UserLoginResponse
import com.weproud.domain.common.dto.UserRegistrationRequest
import com.weproud.domain.common.dto.UserResponse
import com.weproud.domain.common.service.AuthenticationService
import com.weproud.domain.common.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 인증 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    /**
     * 사용자 등록
     *
     * @param request 사용자 등록 요청
     * @return 등록된 사용자 정보
     */
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: UserRegistrationRequest): ResponseEntity<UserResponse> {
        val userResponse = userService.registerUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
    }

    /**
     * 사용자 로그인
     *
     * @param request 로그인 요청
     * @return 로그인 응답 (토큰, 사용자 정보)
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: UserLoginRequest): ResponseEntity<UserLoginResponse> {
        val loginResponse = authenticationService.login(request)
        return ResponseEntity.ok(loginResponse)
    }

    /**
     * 토큰 갱신
     *
     * @param refreshToken 리프레시 토큰
     * @return 새 액세스 토큰
     */
    @PostMapping("/refresh")
    fun refreshToken(@RequestParam refreshToken: String): ResponseEntity<Map<String, String>> {
        val newToken = authenticationService.refreshToken(refreshToken)
        return ResponseEntity.ok(mapOf("token" to newToken))
    }

    /**
     * 로그아웃
     *
     * @param token 액세스 토큰
     * @return 성공 메시지
     */
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<Map<String, String>> {
        val accessToken = token.substring(7) // "Bearer " 제거
        authenticationService.logout(accessToken)
        return ResponseEntity.ok(mapOf("message" to "로그아웃 되었습니다"))
    }

    /**
     * 이메일 중복 확인
     *
     * @param email 확인할 이메일
     * @return 중복 여부
     */
    @GetMapping("/check-email")
    fun checkEmail(@RequestParam email: String): ResponseEntity<Map<String, Boolean>> {
        val exists = userService.isEmailExists(email)
        return ResponseEntity.ok(mapOf("exists" to exists))
    }
}
