package com.giant.common.config.security.type

enum class Role(val code: String, val authority: String) {
    ADMIN("ADMIN", "ROLE_ADMIN"),
    USER("USER", "ROLE_USER"),
    LEFT("LEFT", "ROLE_LEFT"),
    GUEST("GUEST", "ROLE_GUEST");

    companion object {
        fun fromCode(code: String?): Role =
            Role.entries.find { it.code == code } ?: GUEST
    }
}