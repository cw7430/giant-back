package com.giant.common.config.security

enum class Role(val code: String, val authority: String) {
    CEO("1", "ROLE_CEO"),
    MANAGER("2", "ROLE_MANAGER"),
    EMPLOYEE("3", "ROLE_EMPLOYEE"),
    EXTERNAL("4", "ROLE_EXTERNAL"),
    GUEST("5", "ROLE_GUEST");

    companion object {
        fun fromCode(code: String?): Role =
            Role.entries.find { it.code == code } ?: GUEST
    }
}