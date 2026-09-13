package com.expensemanagementapp.Validation.Custom.Impl;

import com.expensemanagementapp.Validation.Custom.FieldMatching;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldMatchingValidator implements ConstraintValidator<FieldMatching, Object> {

    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldMatching constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        if (object == null) {
            return true;
        }

        try {
            Field field1 = object.getClass().getDeclaredField(firstField);
            Field field2 = object.getClass().getDeclaredField(secondField);

            field1.setAccessible(true);
            field2.setAccessible(true);

            Object value1 = field1.get(object);
            Object value2 = field2.get(object);

            boolean matches = Objects.equals(value1, value2);

            if (!matches) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(firstField)
                        .addConstraintViolation();

                context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(secondField)
                        .addConstraintViolation();
            }

            return matches;

        } catch (Exception e) {
            return false;
        }
    }
}
