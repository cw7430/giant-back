package com.giant.module.employee.dto.request

import com.giant.common.api.request.PageRequest
import com.giant.common.api.type.SortOrder
import com.giant.module.employee.dto.type.EmployeeProfileSortPath

data class EmployeeProfilesRequestDto(
    override val page: Int = 1,

    override val size: Int = 10,

    override val blockSize: Int = 10,

    val sortPath: EmployeeProfileSortPath = EmployeeProfileSortPath.EMPLOYEE,

    val sortOrder: SortOrder = SortOrder.ASC,
) : PageRequest(page, size, blockSize)