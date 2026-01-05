package com.giant.common.api.response;

import lombok.Getter;

@Getter
public abstract sealed class ResponseDto
        permits SuccessResponseDto, ErrorResponseDto {

    protected final String code;
    protected final String message;

    protected ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}