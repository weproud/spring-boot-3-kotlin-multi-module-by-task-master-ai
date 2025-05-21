package com.weproud.domain.rds.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    var email: String,
    
    @Column(name = "password", nullable = false, length = 100)
    var password: String,
    
    @Column(name = "name", nullable = false, length = 50)
    var name: String,
    
    @Column(name = "nickname", length = 50)
    var nickname: String? = null,
    
    @Column(name = "phone_number", length = 20)
    var phoneNumber: String? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    var role: UserRole = UserRole.USER,
    
    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null
) : BaseEntity() {
    
    fun updateProfile(name: String, nickname: String?, phoneNumber: String?) {
        this.name = name
        this.nickname = nickname
        this.phoneNumber = phoneNumber
    }
    
    fun updatePassword(password: String) {
        this.password = password
    }
    
    fun updateRole(role: UserRole) {
        this.role = role
    }
    
    fun recordLogin() {
        this.lastLoginAt = LocalDateTime.now()
    }
}

enum class UserRole {
    USER, ADMIN
}
