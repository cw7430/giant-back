package com.giant.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "refresh_token",
        schema = "auth",
        indexes = {
                @Index(name = "fk_token_account", columnList = "user_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long refreshTokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_token_account"))
    private Account account;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(
            name = "expired_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(3) WITHOUT TIME ZONE"
    )
    private Instant expiredAt;

    public static RefreshToken create(Account account, String token, Instant expiredAt) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.account = account;
        refreshToken.token = token;
        refreshToken.expiredAt = expiredAt;
        return refreshToken;
    }
}
