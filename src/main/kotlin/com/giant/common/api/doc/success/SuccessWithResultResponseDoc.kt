package com.giant.common.api.doc.success

import io.swagger.v3.oas.annotations.media.Schema

abstract class SuccessWithResultResponseDoc<T> : SuccessResponseDoc() {
    @get:Schema(description = "응답 결과")
    open var result: T? = null
}