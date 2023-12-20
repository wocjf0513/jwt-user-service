package com.example.jwt.domain.member.annotation.validator;

import com.example.jwt.domain.member.annotation.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private final String PHONE_REGEX = "^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$";

    @Override
    public void initialize(Phone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(PHONE_REGEX, value);
    }
}
