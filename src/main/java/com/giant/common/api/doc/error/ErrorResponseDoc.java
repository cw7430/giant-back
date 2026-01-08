package com.giant.common.api.doc.error;

import com.giant.common.api.type.ValidationError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

public class ErrorResponseDoc {
    /** 400 Bad Request - Validation Error */
    @Getter
    public static class BadRequest {
        @Schema(example = "VE")
        private final String code = null;
        @Schema(example = "입력값이 잘못되었습니다.")
        private final String message = null;

        @Schema(description = "에러내용")
        private final List<ValidationError> validationErrors = null;
    }

    /** 401 Unauthorized */
    @Getter
    public static class Unauthorized {
        @Schema(example = "UA")
        private final String code = null;
        @Schema(example = "로그인이 필요합니다.")
        private final String message = null;
    }
    /** 401 Unauthorized */
    @Getter
    public static class LoginError {
        @Schema(example = "LGE")
        private final String code = null;
        @Schema(example = "아이디 또는 비밀번호가 잘못되었습니다.")
        private final String message = null;
    }

    /** 403 Forbidden */
    @Getter
    public static class Forbidden {
        @Schema(example = "FB")
        private final String code = null;
        @Schema(example = "접근 권한이 없습니다.")
        private final String message = null;
    }

    /** 404 Not Found */
    @Getter
    public static class ResourceNotFound {
        @Schema(example = "RNF")
        private final String code = null;
        @Schema(example = "요청한 리소스를 찾을 수 없습니다.")
        private final String message = null;
    }

    /** 404 Not Found */
    @Getter
    public static class EndpointNotFound {
        @Schema(example = "ENF")
        private final String code = null;
        @Schema(example = "요청한 경로가 잘못되었습니다.")
        private final String message = null;
    }

    /** 409 Conflict */
    @Getter
    public static class DuplicateResource {
        @Schema(example = "DR")
        private final String code = null;
        @Schema(example = "이미 존재하는 항목입니다.")
        private final String message = null;
    }

    /** 409 Conflict */
    @Getter
    public static class Conflict {
        @Schema(example = "CF")
        private final String code = null;
        @Schema(example = "요청이 현재 상태와 충돌합니다.")
        private final String message = null;
    }

    /** 500 Internal Server Error */
    @Getter
    public static class InternalServerError {
        @Schema(example = "ISE")
        private final String code = null;
        @Schema(example = "서버에서 문제가 발생했습니다.")
        private final String message = null;
    }
}
