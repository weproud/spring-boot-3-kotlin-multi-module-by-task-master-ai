package com.weproud.domain.rds.entity.user

import com.weproud.domain.common.constant.Status
import com.weproud.domain.rds.entity.BaseEntity
import com.weproud.domain.rds.entity.order.OrderEntity
import jakarta.persistence.*

/**
 * 사용자 상태 열거형
 */
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    DELETED
}

/**
 * 사용자 엔티티
 */
@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false, unique = true)
    val email: String,
    
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = false)
    var password: String,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var userStatus: UserStatus = UserStatus.ACTIVE,
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val roles: MutableSet<String> = mutableSetOf("USER"),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders: MutableList<OrderEntity> = mutableListOf()
) : BaseEntity() {
    
    /**
     * 사용자 상태 업데이트
     */
    fun updateStatus(status: UserStatus) {
        this.userStatus = status
    }
    
    /**
     * 사용자 이름 업데이트
     */
    fun updateName(name: String) {
        this.name = name
    }
    
    /**
     * 사용자 비밀번호 업데이트
     */
    fun updatePassword(password: String) {
        this.password = password
    }
    
    /**
     * 역할 추가
     */
    fun addRole(role: String) {
        this.roles.add(role)
    }
    
    /**
     * 역할 제거
     */
    fun removeRole(role: String) {
        this.roles.remove(role)
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as UserEntity
        
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
    
    override fun toString(): String {
        return "UserEntity(id=$id, email='$email', name='$name', userStatus=$userStatus, status=$status)"
    }
}
