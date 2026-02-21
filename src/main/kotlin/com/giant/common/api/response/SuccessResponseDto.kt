package com.giant.common.api.response;

import com.giant.common.api.type.ResponseCode;


sealed class SuccessResponseDto : ResponseDto() {

    object Simple : SuccessResponseDto() {
        override val code: String = ResponseCode.SUCCESS.code
        override val message: String = ResponseCode.SUCCESS.message
    }

    data class WithResult<T>(
        val result: T
    ) : SuccessResponseDto() {
        override val code: String = ResponseCode.SUCCESS.code
        override val message: String = ResponseCode.SUCCESS.message
    }
}