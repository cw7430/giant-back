package com.giant.common.api.doc.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class SuccessWithResultResponseDoc<T>
        extends SuccessResponseDoc {

    @Schema(description = "응답 결과")
    protected T result;
}
