package com.expensemanagementapp.Validation.Custom;


import com.expensemanagementapp.Validation.Custom.Impl.DueDateNotBeforePlacedOnDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DueDateNotBeforePlacedOnDateValidator.class)
@Documented
public @interface DueDateNotBeforePlacedOnDate {
    String message() default "Due date must be after today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
