package com.giant.module.employee.doc

import com.giant.common.api.doc.success.SuccessWithResultResponseDoc
import com.giant.module.employee.dto.response.PositionResponseDto
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "PositionsResponse")
class PositionsResponseDoc : SuccessWithResultResponseDoc<List<PositionResponseDto>>() {
    @get:ArraySchema(schema = Schema(implementation = PositionResponseDto::class))
    override var result: List<PositionResponseDto>? = null
}