package com.cert.manage.member.service;

import com.cert.manage.common.util.jwt.JwtProvider;
import com.cert.manage.member.entity.Member;
import com.cert.manage.member.repository.MemberRepository;
import com.cert.manage.member.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements MemberServiceInterface{

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void signUp (SignUp signUp) {
        memberRepository.save(Member.of(signUp));
    }

    // 이메일 변경 시 이메일로 인증코드 보내서 인증번호 확인 후 변경
    public void changeEmail () {

    }

    @Override
    public Token signIn(SignIn signIn) {
        Member member = memberRepository.findByEmail(signIn.getEmail()).orElseThrow();
        signIn.checkingSignIn(signIn).orElseThrow();
        return Token.of(member, jwtProvider);
    }

    @Override
    public MyInfo myInfo (MemberId memberId) {
        return MyInfo.of(findMember(memberId));
    }


    @Override
    public Member findMember(MemberId memberId) {
        return memberRepository.findById(memberId.getMemberId())
                .orElseThrow();
    }
}
