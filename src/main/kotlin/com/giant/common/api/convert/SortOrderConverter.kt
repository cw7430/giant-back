package com.giant.common.api.convert

import com.giant.common.api.type.SortOrder
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class SortOrderConverter: Converter<String, SortOrder?> {
    override fun convert(source: String): SortOrder? {
        return SortOrder.entries.find { it.name.equals(source, ignoreCase = true) }
    }
}