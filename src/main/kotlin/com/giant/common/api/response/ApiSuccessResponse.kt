package com.giant.common.api.response

import com.giant.common.api.code.ResponseCode

sealed class ApiSuccessResponse : ApiResponse() {

    object Simple : ApiSuccessResponse() {
        override val code: String = ResponseCode.SUCCESS.code
        override val message: String = ResponseCode.SUCCESS.message
    }

    data class WithResult<T>(
        val result: T
    ) : ApiSuccessResponse() {
        override val code: String = ResponseCode.SUCCESS.code
        override val message: String = ResponseCode.SUCCESS.message
    }
}
