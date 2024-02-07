package com.cert.manage.common.util.jwt;


import com.cert.manage.member.entity.Member;
import com.cert.manage.member.service.dto.MemberId;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.password}")
    private String secretKey;

    @Value("${jwt.atk}")
    private Long TOKEN_VALID_TIME;

    @Value("${jwt.rtk}")
    private Long REFRESH_TOKEN_VALID_TIME;


    public String createAccessToken(Member member) {
        Date now = new Date();
        Date expiration = expiration(now, TOKEN_VALID_TIME);
        Claims claims = Jwts.claims();
        claims.put("id", member.getMemberId());
        claims.put("time", expiration);
        return createJwt("ACCESS_TOKEN", now, expiration, claims);
    }

    private Date expiration(Date now, Long validTime){
        return new Date(now.getTime() + validTime);
    }

    public String createRefreshToken(Member member) {
        Date now = new Date();
        Date expiration = expiration(now, REFRESH_TOKEN_VALID_TIME);
        Claims claims = Jwts.claims();
        claims.put("id", member.getMemberId());
        return createJwt("REFRESH_TOKEN", now, expiration, claims);
    }

    public String createJwt(String subject, Date now,  Date expiration, Claims claim) {

        // 만료기간 1일
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                );

        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }

        jwtBuilder.setExpiration(expiration);

        return jwtBuilder.compact();

    }


    //토큰검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(removeBearer(token));

            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(removeBearer(token)).getBody().getExpiration();

            Date now = new Date();

            return !Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(removeBearer(token))
                    .getBody()
                    .getExpiration()
                    .before(now);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("토큰 검증 실패");
        }
    }

    public MemberId getClaim(String token) {
        Claims claimsBody = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(removeBearer(token))
                .getBody();

        return MemberId.builder()
                .memberId(Long.valueOf((Integer) claimsBody.getOrDefault("id", 0L)))
                .build();
    }

    //==토큰 앞 부분('Bearer') 제거 메소드==//
    private String removeBearer(String token) {
        return token.replace("Bearer", "").trim();
    }
}