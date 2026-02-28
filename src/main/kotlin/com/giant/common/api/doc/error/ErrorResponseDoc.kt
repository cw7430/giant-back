package com.giant.common.api.doc.error

import com.giant.common.api.type.ValidationError
import io.swagger.v3.oas.annotations.media.Schema

class ErrorResponseDoc {

    /** 400 Bad Request - Validation Error */
    class BadRequest {
        @get:Schema(example = "VE")
        val code: String? = null

        @get:Schema(example = "입력값이 잘못되었습니다.")
        val message: String? = null

        @get:Schema(description = "에러내용")
        val validationErrors: List<ValidationError>? = null
    }

    /** 401 Unauthorized */
    class Unauthorized {
        @get:Schema(example = "UA")
        val code: String? = null

        @get:Schema(example = "로그인이 필요합니다.")
        val message: String? = null
    }

    /** 401 Unauthorized */
    class LoginError {
        @get:Schema(example = "LGE")
        val code: String? = null

        @get:Schema(example = "아이디 또는 비밀번호가 잘못되었습니다.")
        val message: String? = null
    }

    /** 401 Unauthorized */
    class PasswordError {
        @get:Schema(example = "PWE")
        val code: String? = null

        @get:Schema(example = "비빌번호가 잘못되었습니다.")
        val message: String? = null
    }

    /** 403 Forbidden */
    class Forbidden {
        @get:Schema(example = "FB")
        val code: String? = null

        @get:Schema(example = "접근 권한이 없습니다.")
        val message: String? = null
    }

    /** 404 Not Found */
    class ResourceNotFound {
        @get:Schema(example = "RNF")
        val code: String? = null

        @get:Schema(example = "요청한 리소스를 찾을 수 없습니다.")
        val message: String? = null
    }

    /** 404 Not Found */
    class EndpointNotFound {
        @get:Schema(example = "ENF")
        val code: String? = null

        @get:Schema(example = "요청한 경로가 잘못되었습니다.")
        val message: String? = null
    }

    /** 409 Conflict */
    class DuplicateResource {
        @get:Schema(example = "DR")
        val code: String? = null

        @get:Schema(example = "이미 존재하는 항목입니다.")
        val message: String? = null
    }

    /** 409 Conflict */
    class Conflict {
        @get:Schema(example = "CF")
        val code: String? = null

        @get:Schema(example = "요청이 현재 상태와 충돌합니다.")
        val message: String? = null
    }

    /** 500 Internal Server Error */
    class InternalServerError {
        @get:Schema(example = "ISE")
        val code: String? = null

        @get:Schema(example = "서버에서 문제가 발생했습니다.")
        val message: String? = null
    }
}