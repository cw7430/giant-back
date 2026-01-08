package com.giant.common;

import com.giant.common.api.doc.error.ErrorResponseDoc;
import com.giant.common.api.doc.success.SuccessResponseDoc;
import com.giant.common.api.response.ResponseDto;
import com.giant.common.api.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Home Controller", description = "공통 API")
public class HomeController {

    @GetMapping("/health-check")
    @Operation(summary = "헬스 체크")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(
                    schema = @Schema(implementation = SuccessResponseDoc.class)
            )),
            @ApiResponse(responseCode = "500", description = "기타오류", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDoc.InternalServerError.class)
            ))
    })
    public ResponseEntity<ResponseDto> healthCheck() {
        return ResponseEntity.ok(SuccessResponseDto.ok());
    }

}
