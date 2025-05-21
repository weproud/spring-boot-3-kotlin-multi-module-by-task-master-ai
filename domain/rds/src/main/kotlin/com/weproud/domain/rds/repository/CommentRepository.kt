package com.weproud.domain.rds.repository

import com.weproud.domain.rds.entity.Comment
import com.weproud.domain.rds.entity.Post
import com.weproud.domain.rds.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<Comment, Long> {
    
    fun findByPost(post: Post, pageable: Pageable): Page<Comment>
    
    fun findByUser(user: User, pageable: Pageable): Page<Comment>
    
    @Query("SELECT c FROM Comment c WHERE c.post = :post AND c.parent IS NULL AND c.deletedAt IS NULL")
    fun findRootCommentsByPost(post: Post, pageable: Pageable): Page<Comment>
    
    @Query("SELECT c FROM Comment c WHERE c.parent = :parent AND c.deletedAt IS NULL")
    fun findRepliesByParent(parent: Comment, pageable: Pageable): Page<Comment>
}
