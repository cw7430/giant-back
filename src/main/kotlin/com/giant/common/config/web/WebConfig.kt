package com.giant.common.config.web

import com.giant.common.config.convert.EmployeeProfileSortPathConverter
import com.giant.common.config.convert.SortOrderConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(SortOrderConverter())
        registry.addConverter(EmployeeProfileSortPathConverter())
    }
}