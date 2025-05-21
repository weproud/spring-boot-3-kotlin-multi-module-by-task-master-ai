package com.weproud.domain.rds.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
    
    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set
    
    fun delete() {
        this.deletedAt = LocalDateTime.now()
    }
    
    fun restore() {
        this.deletedAt = null
    }
    
    val isDeleted: Boolean
        get() = deletedAt != null
}
