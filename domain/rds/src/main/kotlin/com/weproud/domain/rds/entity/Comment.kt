package com.weproud.domain.rds.entity

import jakarta.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Comment? = null,
    
    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    val replies: MutableList<Comment> = mutableListOf()
) : BaseEntity() {
    
    fun updateContent(content: String) {
        this.content = content
    }
    
    fun addReply(reply: Comment) {
        replies.add(reply)
    }
    
    fun removeReply(reply: Comment) {
        replies.remove(reply)
    }
}
