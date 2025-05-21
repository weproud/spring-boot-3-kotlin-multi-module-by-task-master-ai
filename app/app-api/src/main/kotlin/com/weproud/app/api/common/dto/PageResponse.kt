package com.weproud.app.api.common.dto

import org.springframework.data.domain.Page

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
        /**
         * Spring Data Page 객체로부터 PageResponse 생성
         */
        fun <T> from(page: Page<T>): PageResponse<T> {
            return PageResponse(
                content = page.content,
                page = page.number,
                size = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                last = page.isLast,
                first = page.isFirst,
                empty = page.isEmpty
            )
        }
        
        /**
         * 리스트와 페이지 정보로부터 PageResponse 생성
         */
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
