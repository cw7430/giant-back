package com.giant.common.api.response

sealed class ResponseDto {
    abstract val code: String
    abstract val message: String
}