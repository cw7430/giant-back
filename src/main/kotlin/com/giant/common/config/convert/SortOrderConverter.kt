package com.giant.common.config.convert

import com.giant.common.api.type.SortOrder
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class SortOrderConverter : Converter<String, SortOrder> {
    override fun convert(source: String): SortOrder = SortOrder.entries
        .firstOrNull { it.name.equals(source, ignoreCase = true) }
        ?: SortOrder.ASC
}