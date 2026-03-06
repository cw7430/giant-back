package com.giant.module.employee.dto.request

import com.giant.common.api.type.SortOrder
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class EmployeeProfilesRequestDto(
    @field:Min(1, message = "1 이상만 가능합니다.")
    val page: Int = 1,

    @field:Min(1, message = "1 이상 100 이하만 가능합니다.")
    @field:Max(100, message = "1 이상 100 이하만 가능합니다.")
    val size: Int = 10,

    val sortedName: String = "employee_code",

    val sortedOrder: SortOrder = SortOrder.ASC,
)