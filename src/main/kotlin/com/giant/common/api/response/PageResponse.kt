package com.giant.common.api.response

import com.giant.common.api.request.PageRequest
import kotlin.math.ceil

data class PageResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentPage: Int,
    val size: Int,
    val startPage: Int,
    val endPage: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
) {
    companion object {
        fun <T> of(content: List<T>, pageRequest: PageRequest, totalElements: Long): PageResponse<T> {
            val totalPages = ceil(totalElements.toDouble() / pageRequest.size).toInt()

            val startPage = ((pageRequest.page - 1) / pageRequest.blockSize) * pageRequest.blockSize + 1
            var endPage = startPage + pageRequest.blockSize - 1
            if (endPage > totalPages) endPage = totalPages

            return PageResponse(
                content = content,
                totalElements = totalElements,
                totalPages = totalPages,
                currentPage = pageRequest.page,
                size = pageRequest.size,
                startPage = startPage,
                endPage = endPage,
                hasNext = endPage < totalPages,
                hasPrevious = startPage > 1
            )
        }
    }
}