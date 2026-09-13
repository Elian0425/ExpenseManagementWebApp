package com.expensemanagementapp.Model;

import com.expensemanagementapp.Validation.Custom.DueDateNotBeforePlacedOnDate;
import com.expensemanagementapp.Validation.Custom.NotDefaultEnum;
import com.expensemanagementapp.Validation.Group.DebtorAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtUpdateValidationGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DebtorDebtModel {

    @NotEmpty(
            message = "This field can't be empty",
            groups = {
                    DebtorDebtAddValidationGroup.class
            }

    )
    @Pattern(
            regexp = "^$|^[A-Za-z]+$",
            message = "Only letters are allowed. Ex: Target.",
            groups = {
                    DebtorDebtAddValidationGroup.class,
                    DebtorDebtUpdateValidationGroup.class
            }
    )
    private String debtorDebtName;

    @NotNull(
            message = "This field can't be empty",
            groups = {
                    DebtorDebtAddValidationGroup.class
            }
    )
    @DecimalMin(
            value = "0.01",
            message = "Amount must be greater than 0.",
            groups = {
                    DebtorDebtAddValidationGroup.class
            }
    )
    private double debtorDebtAmount;

    @NotEmpty(
            message = "This field can't be empty",
            groups = {
                    DebtorDebtAddValidationGroup.class
            }
    )

//    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-\\d{4}$",
//            message = "Invalid date format. Ex: 01-01-2000.",
//            groups = {
//                    DebtorDebtAddValidationGroup.class,
//                    DebtorDebtUpdateValidationGroup.class
//            }
//    )
    @DueDateNotBeforePlacedOnDate(
            message = "Due date must be after today (placed on) date.",
            groups = {
                    DebtorDebtAddValidationGroup.class,
                    DebtorDebtUpdateValidationGroup.class
            }
    )
    private String debtorDebtDueDate;

    @NotDefaultEnum(
            groups = {
                    DebtorDebtAddValidationGroup.class
            }
    )
    private String debtorDebtStatus;

    @NotDefaultEnum(
            groups = {
                    DebtorDebtAddValidationGroup.class
            }
    )
    private String debtorDebtType;

    private String debtorDebtDescription;
}
