package com.giant.common.api.response

sealed class ErrorResponseDto : ResponseDto() {

    data class Simple(
        override val code: String,
        override val message: String
    ) : ErrorResponseDto()

    data class WithErrors<T>(
        override val code: String,
        override val message: String,
        val errors: T
    ) : ErrorResponseDto()
}