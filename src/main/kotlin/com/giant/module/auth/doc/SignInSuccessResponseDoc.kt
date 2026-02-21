package com.giant.module.auth.doc

import com.giant.common.api.doc.success.SuccessWithResultResponseDoc
import com.giant.module.auth.dto.response.SignInResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "SignInSuccessResponse")
class SignInSuccessResponseDoc: SuccessWithResultResponseDoc<SignInResponseDto>() {
    @get:Schema(implementation = SignInResponseDto::class)
    override var result: SignInResponseDto? = null
}