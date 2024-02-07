package com.cert.manage.member.service.dto;

import com.cert.manage.common.util.jwt.JwtProvider;
import com.cert.manage.member.entity.Member;
import io.jsonwebtoken.Jwt;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;

    public static Token of (Member member, JwtProvider jwtProvider) {
        return Token.builder()
                .accessToken(jwtProvider.createAccessToken(member))
                .refreshToken(jwtProvider.createRefreshToken(member))
                .build();
    }
}
