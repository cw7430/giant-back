package com.giant.common.api.response

sealed class ApiResponse {
    abstract val code: String
    abstract val message: String
}