package com.giant.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "account_role", schema = "auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountRole {
    @Id
    @Column(name = "id", nullable = false)
    private Long accountRoleId;

    @Column(
            name = "role_name",
            nullable = false,
            unique = true
    )
    private String accountRoleName;

    @OneToMany(mappedBy = "accountRole", fetch = FetchType.LAZY)
    private List<Account> accounts;
}
