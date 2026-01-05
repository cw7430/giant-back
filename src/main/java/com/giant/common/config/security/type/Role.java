package com.giant.common.config.security.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ADMIN("1", "ROLE_ADMIN"),
    USER("2", "ROLE_USER"),
    LEAVING("3", "ROLE_LEAVING"),
    GUEST("4", "ROLE_GUEST");

    private final String code;
    private final String authority;

    Role(String code, String authority) {
        this.code = code;
        this.authority = authority;
    }

    public static Role fromCode(String code) {
        return Arrays.stream(Role.values())
                .filter(role -> role.code.equals(code))
                .findFirst()
                .orElse(GUEST);
    }
}