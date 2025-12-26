package com.giant.common.api.response

sealed class ApiSuccessResponse : ApiResponse() {

    data class Simple(
        override val code: String,
        override val message: String
    ) : ApiSuccessResponse()

    data class WithResult<T>(
        override val code: String,
        override val message: String,
        val result: T
    ) : ApiSuccessResponse()
}