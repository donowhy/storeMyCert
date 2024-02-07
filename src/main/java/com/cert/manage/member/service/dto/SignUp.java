package com.cert.manage.member.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignUp {

    private String email;
    private String password;
    private String phoneNumber;
}
