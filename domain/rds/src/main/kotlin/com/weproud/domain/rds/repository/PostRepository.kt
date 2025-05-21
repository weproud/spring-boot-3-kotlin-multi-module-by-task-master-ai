package com.weproud.domain.rds.repository

import com.weproud.domain.rds.entity.Post
import com.weproud.domain.rds.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<Post, Long> {
    
    fun findByUser(user: User, pageable: Pageable): Page<Post>
    
    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL")
    fun findAllActive(pageable: Pageable): Page<Post>
    
    @Query("SELECT p FROM Post p WHERE p.user = :user AND p.deletedAt IS NULL")
    fun findActiveByUser(user: User, pageable: Pageable): Page<Post>
    
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword% AND p.deletedAt IS NULL")
    fun searchByKeyword(keyword: String, pageable: Pageable): Page<Post>
}
