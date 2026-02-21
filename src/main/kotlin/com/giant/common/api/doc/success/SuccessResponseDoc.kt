package com.giant.common.api.doc.success

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "SuccessResponse")
abstract class SuccessResponseDoc {
    @get:Schema(example = "SU")
    val code: String? = null
    @get:Schema(example = "요청이 성공적으로 처리되었습니다.")
    val message: String? = null
}