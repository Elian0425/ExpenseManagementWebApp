package com.expensemanagementapp.Model;

import com.expensemanagementapp.Validation.Group.DebtorDebtPaymentAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtPaymentUpdateValidationGroup;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DebtorDebtPaymentModel {

    @NotNull(
            message = "Field can't be empty.",
            groups = {
                    DebtorDebtPaymentAddValidationGroup.class
            }

    )
    @DecimalMin(
            value = "0.01",
            message = "Debt amount must be greater than zero.",
            groups = {
                    DebtorDebtPaymentAddValidationGroup.class
            }
    )
    private double debtorDebtPaymentAmount;

    private String debtorDebtDescription;
}
