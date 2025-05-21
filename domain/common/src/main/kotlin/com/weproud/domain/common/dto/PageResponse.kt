package com.weproud.domain.common.dto

/**
 * 페이지네이션 응답 DTO
 */
data class PageResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean,
    val first: Boolean,
    val empty: Boolean
) {
    companion object {
        fun <T> of(content: List<T>, page: Int, size: Int, totalElements: Long): PageResponse<T> {
            val totalPages = if (size > 0) (totalElements + size - 1) / size else 0
            val last = page >= totalPages - 1
            val first = page == 0
            val empty = content.isEmpty()
            
            return PageResponse(
                content = content,
                page = page,
                size = size,
                totalElements = totalElements,
                totalPages = totalPages.toInt(),
                last = last,
                first = first,
                empty = empty
            )
        }
    }
}
