package com.expensemanagementapp.Validation.Custom;

import com.expensemanagementapp.Validation.Custom.Impl.NotDefaultEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = NotDefaultEnumValidator.class)
@Target(value = {FIELD})
@Retention(value = RUNTIME)
public @interface NotDefaultEnum {
    String message() default "Field is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
