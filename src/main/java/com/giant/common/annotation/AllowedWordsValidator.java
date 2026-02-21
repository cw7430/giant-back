package com.giant.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class AllowedWordsValidator implements ConstraintValidator<AllowedWords, String> {
    private Set<String> allowedWords;

    @Override
    public void initialize(AllowedWords constraintAnnotation) {
        allowedWords = Set.of(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return allowedWords.contains(value);
    }
}
