package com.weproud.domain.rds.entity

import com.weproud.domain.common.constant.Status
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 모든 엔티티의 기본 클래스
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: Status = Status.ACTIVE
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime
    
    @LastModifiedDate
    @Column(nullable = false)
    lateinit var updatedAt: LocalDateTime
    
    /**
     * 엔티티 삭제 처리 (소프트 삭제)
     */
    fun delete() {
        this.status = Status.DELETED
    }
}
