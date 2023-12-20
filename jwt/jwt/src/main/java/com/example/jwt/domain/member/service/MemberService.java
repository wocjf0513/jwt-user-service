package com.example.jwt.domain.member.service;

import com.example.jwt.domain.member.dto.request.SignInMemberRequest;
import com.example.jwt.domain.member.dto.request.SignUpMemberRequest;
import com.example.jwt.domain.member.dto.request.UpdateMemberPasswordRequest;
import com.example.jwt.domain.member.dto.response.MemberResponse;
import com.example.jwt.domain.member.dto.response.SignInResponse;
import com.example.jwt.domain.member.dto.response.TokenResponse;
import com.example.jwt.domain.member.entity.Member;
import com.example.jwt.domain.member.exception.AlreadyExistEmailException;
import com.example.jwt.domain.member.exception.MemberNotFoundException;
import com.example.jwt.domain.member.repository.MemberRepository;
import com.example.jwt.domain.member.util.MemberMapper;
import com.example.jwt.global.config.CustomUserDetailsService;
import com.example.jwt.global.exception.ErrorCode;
import com.example.jwt.global.jwt.util.JwtUtil;
import com.example.jwt.global.jwt.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final JwtUtil jwtUtil;


    MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        SecurityUtil securityUtil, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
        this.jwtUtil = jwtUtil;
    }

    public MemberResponse signUp(final SignUpMemberRequest signUpMemberRequest) {
        if (memberRepository.existsByEmail(signUpMemberRequest.email())) {
            throw new AlreadyExistEmailException(ErrorCode.ALREADY_EXIST_EMAIL);
        }
        return MemberMapper.toMemberResponse(memberRepository.save(
            MemberMapper.toMember(signUpMemberRequest,
                passwordEncoder.encode(signUpMemberRequest.password()))));
    }

    public SignInResponse signIn(final SignInMemberRequest signInMemberRequest) {
        Member retrivedMember = memberRepository.findByEmail(signInMemberRequest.email())
            .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (passwordEncoder.matches(signInMemberRequest.password(), retrivedMember.getPassword())) {
            String accessToken = jwtUtil.generateToken(MemberMapper.toUserDetails(retrivedMember));
            String refreshToken = jwtUtil.generateRefreshToken(retrivedMember.getName());
            MemberResponse memberResponse = MemberMapper.toMemberResponse(retrivedMember);
            TokenResponse tokenResponse = MemberMapper.toTokenResponse(accessToken, refreshToken);
            return MemberMapper.toSignInResponse(memberResponse, tokenResponse);
        } else {

        }



    }

    @PatchMapping
    public void updateMemberPassword(
        final UpdateMemberPasswordRequest updateMemberPasswordRequest) {
        Member member = memberRepository.findById(securityUtil.getCurrentMemberId())
            .orElseThrow(() -> new MemberNotFoundException(
                ErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(passwordEncoder.encode(updateMemberPasswordRequest.password()));
    }

}
