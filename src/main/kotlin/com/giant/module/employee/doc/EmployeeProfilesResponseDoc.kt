package com.giant.module.employee.doc

import com.giant.common.api.doc.success.SuccessWithResultResponseDoc
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "EmployeeProfilesResponse")
class EmployeeProfilesResponseDoc : SuccessWithResultResponseDoc<EmployeeProfilesPageResponseDoc>() {
    @get:Schema(implementation = EmployeeProfilesPageResponseDoc::class)
    override var result: EmployeeProfilesPageResponseDoc? = null
}