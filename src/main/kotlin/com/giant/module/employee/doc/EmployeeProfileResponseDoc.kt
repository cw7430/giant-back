package com.giant.module.employee.doc

import com.giant.common.api.doc.success.SuccessWithResultResponseDoc
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "EmployeeProfilesResponse")
class EmployeeProfileResponseDoc : SuccessWithResultResponseDoc<EmployeeProfileResponseDto>() {
    @get:Schema(implementation = EmployeeProfileResponseDto::class)
    override var result: EmployeeProfileResponseDto? = null
}
