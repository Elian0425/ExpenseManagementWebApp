package com.expensemanagementapp.Validation.Custom.Impl;

import com.expensemanagementapp.Validation.Custom.NotDefaultEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NotDefaultEnumValidator implements ConstraintValidator<NotDefaultEnum, String> {

    @Override
    public boolean isValid(String fieldName, ConstraintValidatorContext constraintValidatorContext) {
        return !Objects.equals(fieldName, "default");
    }
}
