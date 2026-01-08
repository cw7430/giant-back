package com.giant.auth.doc;

import com.giant.auth.dto.response.SignInResponseDto;
import com.giant.common.api.doc.success.SuccessWithResultResponseDoc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "SignInSuccessResponse")
@Getter
public class SignInSuccessResponseDoc
        extends SuccessWithResultResponseDoc<SignInResponseDto> {

    @Schema(implementation = SignInResponseDto.class)
    private final SignInResponseDto result = null;
}