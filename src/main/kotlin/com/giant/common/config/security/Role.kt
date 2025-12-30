package com.giant.common.config.security

enum class Role(val code: String, val authority: String) {
    ADMIN("1", "ROLE_ADMIN"),
    USER("2", "ROLE_USER"),
    LEAVING("3", "ROLE_LEAVING"),
    GUEST("4", "ROLE_GUEST");

    companion object {
        fun fromCode(code: String?): Role =
            Role.entries.find { it.code == code } ?: GUEST
    }
}