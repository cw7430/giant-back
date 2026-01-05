package com.giant.common.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

@Component
public class CookieUtil {
    private final boolean isSecured;

    public CookieUtil(@Value("${spring.profiles.active}") String activeProfile) {
        isSecured = !"local".equals(activeProfile);
    }

    public void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            long maxAge,
            boolean isHttpOnly
    ) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(isHttpOnly)
                .maxAge(maxAge)
                .sameSite("Lax")
                .secure(isSecured)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void removeCookie(HttpServletResponse response, String name, boolean isHttpOnly) {
        addCookie(response, name, "", 0, isHttpOnly);
    }
}
