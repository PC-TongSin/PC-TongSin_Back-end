package com.computatongsin.computatongsin.controller;

import com.computatongsin.computatongsin.dto.ResponseDto;
import com.computatongsin.computatongsin.dto.TokenDto;
import com.computatongsin.computatongsin.dto.req.CheckidDto;
import com.computatongsin.computatongsin.dto.req.LoginReqDto;
import com.computatongsin.computatongsin.dto.req.SignupReqDto;
import com.computatongsin.computatongsin.dto.req.TokenRequestDto;
import com.computatongsin.computatongsin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody @Valid SignupReqDto signupReqDto) {
        return memberService.createAccount(signupReqDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginReqDto) {
        return memberService.loginAccount(loginReqDto);
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        System.out.println(tokenRequestDto);
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }

    // 아이디 중복확인
    @PostMapping("/checkid")
    public ResponseDto<?> checkId(@RequestBody CheckidDto checkidDto) {
        return memberService.duplicateCheckId(checkidDto.getUsername());
    }
}
