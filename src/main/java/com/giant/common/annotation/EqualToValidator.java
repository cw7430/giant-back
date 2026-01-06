package com.giant.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;

import java.util.Objects;

public class EqualToValidator implements ConstraintValidator<EqualTo, Object> {
    private String field;
    private String equalTo;

    @Override
    public void initialize(EqualTo constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.equalTo = constraintAnnotation.equalTo();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
            Object fieldValue = beanWrapper.getPropertyValue(field);
            Object equalToValue = beanWrapper.getPropertyValue(equalTo);

            boolean isValid = Objects.equals(fieldValue, equalToValue);

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                context.getDefaultConstraintMessageTemplate()
                        )
                        .addPropertyNode(equalTo)
                        .addConstraintViolation();
            }

            return isValid;
        } catch (NotReadablePropertyException e) {
            throw new IllegalStateException(
                    "@EqualTo 필드명이 잘못되었습니다: " + field + ", " + equalTo, e
            );
        } catch (Exception e) {
            return false;
        }
    }
}
