package com.example.jwt.domain.member.exception;

import com.example.jwt.global.exception.ApplicationException;
import com.example.jwt.global.exception.ErrorCode;

public class MemberNotFoundException extends ApplicationException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
