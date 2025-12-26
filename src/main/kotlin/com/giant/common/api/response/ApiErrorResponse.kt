package com.giant.common.api.response

sealed class ApiErrorResponse : ApiResponse() {

    data class Simple(
        override val code: String,
        override val message: String
    ) : ApiErrorResponse()

    data class WithErrors<T>(
        override val code: String,
        override val message: String,
        val errors: T
    ) : ApiErrorResponse()
}