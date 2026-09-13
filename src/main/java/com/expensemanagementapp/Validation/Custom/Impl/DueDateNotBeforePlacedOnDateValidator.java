package com.expensemanagementapp.Validation.Custom.Impl;

import com.expensemanagementapp.Validation.Custom.DueDateNotBeforePlacedOnDate;
import jakarta.validation.ConstraintValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DueDateNotBeforePlacedOnDateValidator implements ConstraintValidator<DueDateNotBeforePlacedOnDate, String> {
    @Override
    public boolean isValid(String dueDateStr, jakarta.validation.ConstraintValidatorContext context) {
        if (dueDateStr == null || dueDateStr.isEmpty()) {
            return true; // Let @NotEmpty handle this case
        }

        try {
            LocalDate dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return dueDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }
    }
}
