package com.giant.common.api.doc.success

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "PageResponse")
abstract class PageResponseDoc<T> {
    @get:Schema(description = "데이터")
    open var content: T? = null

    @get:Schema(description = "데이터 갯수", example = "100")
    val totalElements: Long? = null

    @get:Schema(description = "전체 페이지", example = "20")
    val totalPages: Int? = null

    @get:Schema(description = "현재 페이지", example = "1")
    val currentPage: Int? = null

    @get:Schema(description = "페이지 당 데이터 사이즈", example = "10")
    val size: Int? = null

    @get:Schema(description = "시작 페이지", example = "1")
    val startPage: Int? = null

    @get:Schema(description = "끝 페이지", example = "5")
    val endPage: Int? = null

    @get:Schema(description = "다음 버튼 여부", example = "true")
    val hasNext: Boolean? = null

    @get:Schema(description = "이전 버튼 여부", example = "false")
    val hasPrevious: Boolean? = null
}