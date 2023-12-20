package com.example.jwt.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

public record SignInMemberRequest(
    @NotNull
    @Email(message = "유효하지 않은 이메일 형식입니다.",
        regexp = EMAIL_REGEX)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    String email,
    @NotNull
    @Length(min = 8)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    String password
) {

    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$\n";
    private static final String NOT_BLANK_MESSAGE = "공백은 사용할 수 없습니다.";

    @Builder
    public SignInMemberRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
