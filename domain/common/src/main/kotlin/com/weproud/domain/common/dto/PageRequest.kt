package com.weproud.domain.common.dto

/**
 * 페이지네이션 요청 DTO
 */
data class PageRequest(
    val page: Int = 0,
    val size: Int = 20,
    val sort: String? = null
) {
    fun getOffset(): Int = page * size
    
    fun getLimit(): Int = size
    
    companion object {
        fun of(page: Int, size: Int): PageRequest {
            return PageRequest(page, size)
        }
        
        fun of(page: Int, size: Int, sort: String): PageRequest {
            return PageRequest(page, size, sort)
        }
    }
}
