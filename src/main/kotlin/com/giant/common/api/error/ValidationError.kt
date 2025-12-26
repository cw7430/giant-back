package com.giant.common.api.error

data class ValidationError(
    val field: String,
    val message: String
)