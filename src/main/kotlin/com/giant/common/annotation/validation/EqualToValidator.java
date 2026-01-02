package com.giant.common.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

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

            boolean isValid =
                    (fieldValue == null && equalToValue == null)
                            || (fieldValue != null && fieldValue.equals(equalToValue));

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                context.getDefaultConstraintMessageTemplate()
                        )
                        .addPropertyNode(equalTo)
                        .addConstraintViolation();
            }

            return isValid;
        } catch (Exception e) {
            return false;
        }
    }
}
