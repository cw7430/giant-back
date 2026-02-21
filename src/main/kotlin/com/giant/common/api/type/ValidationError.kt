package com.giant.common.api.type

data class ValidationError(
    val field: String,
    val message: String
)
