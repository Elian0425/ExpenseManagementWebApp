package com.expensemanagementapp.Validation.Custom;
import com.expensemanagementapp.Validation.Custom.Impl.FieldMatchingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = FieldMatchingValidator.class)
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FieldMatchingContainer.class)
public @interface FieldMatching {
    String message() default "Fields do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();

    String secondField();
}


