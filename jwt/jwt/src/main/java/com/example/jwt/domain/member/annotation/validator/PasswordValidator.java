package com.example.jwt.domain.member.annotation.validator;

import com.example.jwt.domain.member.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]{8,}$";

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(PASSWORD_REGEX, value);
    }
}
