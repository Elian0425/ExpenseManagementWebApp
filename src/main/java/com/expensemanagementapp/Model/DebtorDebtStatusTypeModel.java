package com.expensemanagementapp.Model;

import com.expensemanagementapp.Validation.Group.DebtorDebtStatusAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtStatusUpdateValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtTypeAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtTypeUpdateValidationGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DebtorDebtStatusTypeModel {

    @NotEmpty(
            message = "This field can't be empty.",
            groups = {
                    DebtorDebtStatusAddValidationGroup.class,
                    DebtorDebtTypeAddValidationGroup.class,
            }
    )
    @Pattern(
            regexp = "^$|^[A-Za-z]+$",
            message = "This field can only contain letters.",
            groups = {
                    DebtorDebtStatusAddValidationGroup.class,
                    DebtorDebtStatusUpdateValidationGroup.class,
                    DebtorDebtTypeAddValidationGroup.class,
                    DebtorDebtTypeUpdateValidationGroup.class

            }
    )
    private String debtorDebtStatusTypeName;
}
