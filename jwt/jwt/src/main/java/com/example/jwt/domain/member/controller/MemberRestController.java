package com.example.jwt.domain.member.controller;

import com.example.jwt.domain.member.dto.request.SignInMemberRequest;
import com.example.jwt.domain.member.dto.request.SignUpMemberRequest;
import com.example.jwt.domain.member.dto.request.UpdateMemberPasswordRequest;
import com.example.jwt.domain.member.dto.response.MemberResponse;
import com.example.jwt.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member")
public class MemberRestController {

    private final MemberService memberService;

    MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signUp(
        @Valid @RequestBody SignUpMemberRequest signUpMemberRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(memberService.signUp(signUpMemberRequest)),"성공적으로 회원가입 했습니다."));
    }

    @PostMapping("/signin")
    public ResponseEntity<MemberResponse> signIn(
        @Valid @RequestBody SignInMemberRequest signInMemberRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.signIn(signInMemberRequest)),
        "성공적으로 로그인 했습니다."));
    }

    @PatchMapping()
    public ResponseEntity updateMemberPassword(@Valid @RequestBody
    UpdateMemberPasswordRequest updateMemberPasswordRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(memberService.updateMemberPassword(updateMemberPasswordRequest)),"성공적으로 회원 정보를 수정했습니다."));
    }

}
