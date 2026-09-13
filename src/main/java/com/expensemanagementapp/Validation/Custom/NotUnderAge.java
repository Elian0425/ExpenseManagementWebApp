package com.expensemanagementapp.Validation.Custom;

import com.expensemanagementapp.Validation.Custom.Impl.NotDefaultEnumValidator;
import com.expensemanagementapp.Validation.Custom.Impl.NotUnderAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = NotUnderAgeValidator.class)
@Target(value = {FIELD})
@Retention(value = RUNTIME)
public @interface NotUnderAge {
    String message() default "User must be at least 18 years old.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
