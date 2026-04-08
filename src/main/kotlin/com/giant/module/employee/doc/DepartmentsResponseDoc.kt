package com.giant.module.employee.doc

import com.giant.common.api.doc.success.SuccessWithResultResponseDoc
import com.giant.module.employee.dto.response.DepartmentResponseDto
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "DepartmentsResponse")
class DepartmentsResponseDoc : SuccessWithResultResponseDoc<List<DepartmentResponseDto>>() {
    @get:ArraySchema(schema = Schema(implementation = DepartmentResponseDto::class))
    override var result: List<DepartmentResponseDto>? = null
}