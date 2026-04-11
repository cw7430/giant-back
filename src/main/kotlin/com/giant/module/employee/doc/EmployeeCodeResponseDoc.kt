package com.giant.module.employee.doc

import com.giant.common.api.doc.success.SuccessWithResultResponseDoc
import com.giant.module.employee.dto.response.EmployeeCodeResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "EmployeeCodeResponse")

class EmployeeCodeResponseDoc : SuccessWithResultResponseDoc<EmployeeCodeResponseDto>() {
    @get:Schema(implementation = EmployeeCodeResponseDto::class)
    override var result: EmployeeCodeResponseDto? = null
}