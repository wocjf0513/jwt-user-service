package com.example.jwt.domain.member.util;

import com.example.jwt.domain.member.authority.Authority;
import com.example.jwt.domain.member.dto.request.SignUpMemberRequest;
import com.example.jwt.domain.member.dto.response.MemberResponse;
import com.example.jwt.domain.member.dto.response.SignInResponse;
import com.example.jwt.domain.member.dto.response.TokenResponse;
import com.example.jwt.domain.member.entity.Member;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMapper {


    public static Member toMember(SignUpMemberRequest signUpMemberRequest, String encodePassword) {
        return Member.builder()
            .email(signUpMemberRequest.email())
            .password(encodePassword)
            .authority(Authority.ROLE_USER)
            .name(signUpMemberRequest.name())
            .build();
    }

    public static MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
            .id(member.getId())
            .email(member.getEmail())
            .name(member.getName())
            .phone(member.getPhone())
            .build();
    }

    public static TokenResponse toTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public static SignInResponse toSignInResponse(MemberResponse memberResponse,
        TokenResponse tokenResponse) {
        return SignInResponse.builder()
            .memberResponse(memberResponse)
            .tokenResponse(tokenResponse)
            .build();
    }

    public static UserDetails toUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
            member.getAuthority().toString());
        return new User(
            String.valueOf(member.getId()),
            member.getPassword(),
            Collections.singleton(grantedAuthority));
    }

}
