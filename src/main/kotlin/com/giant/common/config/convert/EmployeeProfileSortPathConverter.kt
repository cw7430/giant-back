package com.giant.common.config.convert

import com.giant.module.employee.dto.type.EmployeeProfileSortPath
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class EmployeeProfileSortPathConverter : Converter<String, EmployeeProfileSortPath> {
    override fun convert(source: String): EmployeeProfileSortPath = EmployeeProfileSortPath.entries
        .firstOrNull { it.name.equals(source, ignoreCase = true) }
        ?: EmployeeProfileSortPath.EMPLOYEE
}