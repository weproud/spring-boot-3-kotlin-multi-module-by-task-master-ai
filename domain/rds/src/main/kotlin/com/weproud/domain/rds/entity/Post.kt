package com.weproud.domain.rds.entity

import jakarta.persistence.*

@Entity
@Table(name = "posts")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "title", nullable = false, length = 200)
    var title: String,
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @Column(name = "view_count", nullable = false)
    var viewCount: Int = 0,
    
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()
) : BaseEntity() {
    
    fun updatePost(title: String, content: String) {
        this.title = title
        this.content = content
    }
    
    fun incrementViewCount() {
        this.viewCount++
    }
    
    fun addComment(comment: Comment) {
        comments.add(comment)
    }
    
    fun removeComment(comment: Comment) {
        comments.remove(comment)
    }
}
