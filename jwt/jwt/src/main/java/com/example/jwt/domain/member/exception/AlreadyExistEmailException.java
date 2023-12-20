package com.example.jwt.domain.member.exception;

import com.example.jwt.global.exception.ApplicationException;
import com.example.jwt.global.exception.ErrorCode;

public class AlreadyExistEmailException extends ApplicationException {

    public AlreadyExistEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
