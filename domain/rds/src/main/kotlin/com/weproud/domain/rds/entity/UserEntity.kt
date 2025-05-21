package com.weproud.domain.rds.entity

import com.weproud.domain.common.model.UserRole
import com.weproud.domain.common.model.UserStatus
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

/**
 * 사용자 엔티티
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column
    val nickname: String? = null,

    @Column(name = "phone_number")
    val phoneNumber: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: UserStatus,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: ZonedDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime? = null
)
