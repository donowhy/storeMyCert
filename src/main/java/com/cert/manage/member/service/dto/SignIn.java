package com.cert.manage.member.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignIn {

    private String email;
    private String password;

    public Optional<Boolean> checkingSignIn(SignIn signIn) {
        return Optional.of(this.password.equals(signIn.password) &&
                                  this.email.equals(signIn.email));
    }
}
