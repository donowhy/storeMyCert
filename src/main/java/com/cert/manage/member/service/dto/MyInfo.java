package com.cert.manage.member.service.dto;

import com.cert.manage.member.entity.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class MyInfo {

    private Long memberId;
    private String email;
    private String phoneNumber;

    public static MyInfo of(Member member) {
        return MyInfo.builder()
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
