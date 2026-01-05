package com.giant.common.api.response;

import com.giant.common.api.type.ResponseCode;
import lombok.Getter;

public abstract sealed class SuccessResponseDto extends ResponseDto
        permits SuccessResponseDto.Simple, SuccessResponseDto.WithResult {

    protected SuccessResponseDto() {
        super(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage());
    }

    public static Simple ok() {
        return Holder.INSTANCE;
    }

    public static <T> WithResult<T> ok(T result) {
        return new WithResult<>(result);
    }

    private static final class Holder {
        private static final Simple INSTANCE = new Simple();
    }

    public static final class Simple extends SuccessResponseDto {
    }

    @Getter
    public static final class WithResult<T> extends SuccessResponseDto {
        private final T result;

        private WithResult(T result) {
            this.result = result;
        }
    }
}