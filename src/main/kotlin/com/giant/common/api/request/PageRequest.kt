package com.giant.common.api.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

@Schema(name = "PageRequest")
open class PageRequest(
    @field:Min(1, message = "1 이상만 가능합니다.")
    open val page: Int = 1,

    @field:Min(1, message = "1 이상 100 이하만 가능합니다.")
    @field:Max(100, message = "1 이상 100 이하만 가능합니다.")
    open val size: Int = 5,

    @field:Min(5, message = "5 이상 10 이하만 가능합니다.")
    @field:Max(10, message = "5 이상 10 이하만 가능합니다.")
    open val blockSize: Int = 5,
)
