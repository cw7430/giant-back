package com.giant.common.config.security

enum class Role(val code: String, val authority: String) {
    INTERNAL("1", "ROLE_INTERNAL"),
    EXTERNAL("2", "ROLE_EXTERNAL"),
    GUEST("3", "ROLE_GUEST");

    companion object {
        fun fromCode(code: String?): Role =
            Role.entries.find { it.code == code } ?: GUEST
    }
}