package com.example.jwt.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResponse {

    private final MemberResponse memberResponse;
    private final TokenResponse tokenResponse;

    @Builder
    private SignInResponse(MemberResponse memberResponse, TokenResponse tokenResponse) {
        this.memberResponse = memberResponse;
        this.tokenResponse = tokenResponse;
    }

}
