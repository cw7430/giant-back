package com.giant.module.employee.doc

import com.giant.common.api.doc.success.PageResponseDoc
import com.giant.module.employee.dto.response.EmployeeProfileResponseDto
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

class EmployeeProfilesPageResponseDoc : PageResponseDoc<List<EmployeeProfileResponseDto>>() {
    @get:ArraySchema(schema = Schema(implementation = EmployeeProfileResponseDto::class))
    override var content: List<EmployeeProfileResponseDto>? = null
}