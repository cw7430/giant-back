package com.giant.common.api.exception

import com.giant.common.api.type.ResponseCode

class CustomException(val responseCode: ResponseCode) : RuntimeException(responseCode.message)