package com.cert.manage.member.controller;

import com.cert.manage.common.util.jwt.MemberInfo;
import com.cert.manage.member.service.MemberService;
import com.cert.manage.member.service.dto.*;
import com.cert.manage.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseHandler<Void>> signUp(@RequestBody SignUp signUp) {
        memberService.signUp(signUp);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseHandler<MyInfo>> myInfo(@MemberInfo MemberId memberId) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<MyInfo>builder()
                        .message("get MemberInfo")
                        .statusCode(HttpStatus.OK)
                        .data(memberService.myInfo(memberId))
                        .build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseHandler<Token>> signIn (@RequestBody SignIn signIn) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<Token>builder()
                        .message("sign in success")
                        .statusCode(HttpStatus.CREATED)
                        .data(memberService.signIn(signIn))
                        .build());
    }
}
