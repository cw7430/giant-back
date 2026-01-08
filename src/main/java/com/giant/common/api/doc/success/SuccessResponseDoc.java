package com.giant.common.api.doc.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "SuccessResponse")
@Getter
public abstract class SuccessResponseDoc {
    @Schema(example = "SU")
    private final String code = null;
    @Schema(example = "요청이 성공적으로 처리되었습니다.")
    private final String message = null;
}
