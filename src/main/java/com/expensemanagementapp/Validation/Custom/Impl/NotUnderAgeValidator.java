package com.expensemanagementapp.Validation.Custom.Impl;

import com.expensemanagementapp.Validation.Custom.NotUnderAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NotUnderAgeValidator implements ConstraintValidator<NotUnderAge, String> {
    @Override
    public boolean isValid(String userAge, ConstraintValidatorContext constraintValidatorContext) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate DOB = LocalDate.parse(userAge, formatter);

            LocalDate today = LocalDate.now();

            int requiredAge = 18;

            int age = Period.between(DOB, today).getYears();

            return age >= requiredAge;

        } catch (DateTimeParseException e) {
            // invalid date format passed.
            return true;
        }
    }
}
