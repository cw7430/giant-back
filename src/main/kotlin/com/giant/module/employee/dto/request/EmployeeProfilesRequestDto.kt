package com.giant.module.employee.dto.request

import com.giant.common.api.request.PageRequest
import com.giant.common.api.type.SortOrder
import com.giant.module.employee.dto.type.EmployeeProfileSortPath
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "EmployeeProfilesRequest")
data class EmployeeProfilesRequestDto(
    @get:Schema(description = "페이지 번호", example = "1")
    override val page: Int = 1,

    @get:Schema(description = "페이지 당 데이터 사이즈", example = "10")
    override val size: Int = 10,

    @get:Schema(description = "페이지 블록 사이즈", example = "5")
    override val blockSize: Int = 5,

    @get:Schema(description = "정렬 할 열", example = "employee")
    val sortPath: EmployeeProfileSortPath = EmployeeProfileSortPath.EMPLOYEE,

    @get:Schema(description = "정렬 방식", example = "asc")
    val sortOrder: SortOrder = SortOrder.ASC,
) : PageRequest(page, size, blockSize)