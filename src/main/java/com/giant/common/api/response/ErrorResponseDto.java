package com.giant.common.api.response;

import com.giant.common.api.type.ResponseCode;
import lombok.Getter;

@Getter
public abstract sealed class ErrorResponseDto extends ResponseDto
        permits ErrorResponseDto.Simple, ErrorResponseDto.WithErrors {

    protected ErrorResponseDto(String code, String message) {
        super(code, message);
    }

    public static Simple from(ResponseCode code) {
        return new Simple(code.getCode(), code.getMessage());
    }

    public static <T> WithErrors<T> from(ResponseCode code, T errors) {
        return new WithErrors<>(code.getCode(), code.getMessage(), errors);
    }

    public static final class Simple extends ErrorResponseDto {
        private Simple(String code, String message) {
            super(code, message);
        }
    }

    @Getter
    public static final class WithErrors<T> extends ErrorResponseDto {
        private final T errors;

        private WithErrors(String code, String message, T errors) {
            super(code, message);
            this.errors = errors;
        }
    }
}
