package com.giant.common.util

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie

fun addCookie(
    response: HttpServletResponse,
    name:String,
    value:String,
    isHttpOnly: Boolean,
    maxAgeInSeconds:Long
)  {
    val cookie = ResponseCookie.from(name, value)
        .path("/")
        .httpOnly(isHttpOnly)
        .secure(true)
        .maxAge(maxAgeInSeconds)
        .sameSite("Lax")
        .build()

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
}

fun removeCookie(
    response: HttpServletResponse,
    name:String,
    isHttpOnly: Boolean,
) {
    val cookie =  ResponseCookie.from(name, "")
        .path("/")
        .httpOnly(isHttpOnly)
        .secure(true)
        .maxAge(0)
        .sameSite("Lax")
        .build()

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
}