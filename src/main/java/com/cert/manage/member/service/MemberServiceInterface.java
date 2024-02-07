package com.cert.manage.member.service;

import com.cert.manage.member.entity.Member;
import com.cert.manage.member.service.dto.*;

public interface MemberServiceInterface {
    void signUp (SignUp signUp);
    Token signIn (SignIn signIn);
    MyInfo myInfo (MemberId memberId);
    Member findMember (MemberId memberId);
}
