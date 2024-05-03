package com.company.valid;

import com.company.dto.request.LoginRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatchers, Object> {

    @Override
    public void initialize(PasswordMatchers constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LoginRequest user = (LoginRequest) value;
        return user.password().equals(user.passwordConfirm());
    }
}
