package com.example.jwt.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateMemberPasswordRequest(
    @NotNull
    @Length(min = 8)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    String password
) {
    private static final String NOT_BLANK_MESSAGE = "공백은 사용할 수 없습니다.";

    public UpdateMemberPasswordRequest (String password) {
        this.password = password;
    }

}
