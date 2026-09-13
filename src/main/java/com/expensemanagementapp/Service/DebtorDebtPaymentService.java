package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Entity.DebtorDebtPayment;
import com.expensemanagementapp.Model.DebtorDebtPaymentModel;

import java.util.List;

public interface DebtorDebtPaymentService {
    List<DebtorDebtPayment> findAllDebtorDebtPaymentByDebtorDebt(DebtorDebt debtorDebt);

    void addDebtorDebtPayment(DebtorDebt debtorDebt, DebtorDebtPaymentModel debtorDebtPaymentModel);

    DebtorDebtPayment findDebtorDebtPaymentById(int debtorDebtPaymentId);

    void updateDebtorDebtPayment(DebtorDebtPaymentModel debtorDebtPaymentModel, DebtorDebtPayment debtorDebtPayment);

    void deleteDebtorDebtPayment(DebtorDebtPayment debtorDebtPayment);
}
