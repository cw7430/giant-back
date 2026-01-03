package com.giant.common.api.response

data class PageResponseDto<T>(
    val page: Int,
    val size: Int,
    val totalCount: Int,
    val data: List<T>
)