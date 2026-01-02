package com.giant.common.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EqualToValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualTo {
    String message() default "필드 값이 일치하지 않습니다.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String field();
    String equalTo();

    /**
     * Defines several {@code @EqualTo} constraints on the same element.
     *
     * @see EqualTo
     */
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EqualTo[] value();
    }
}
