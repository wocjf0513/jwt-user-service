package com.example.jwt.domain.member.annotation;

import com.example.jwt.domain.member.annotation.validator.PhoneValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default  "입력된 핸드폰 번호 형식이 맞지 않습니다.";


}
