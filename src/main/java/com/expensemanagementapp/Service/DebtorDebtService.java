package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Model.DebtorDebtModel;

import java.util.List;

public interface DebtorDebtService {
    List<DebtorDebt> findAllDebtorDebtsByDebtorId(int debtorDebtId);

    DebtorDebt findDebtorDebtById(int debtorDebtId);

    void addDebtorDebt(DebtorDebtModel debtorDebtModel, Debtor debtor);

    void updateDebtorDebt(DebtorDebtModel debtorDebtModel, DebtorDebt debtorDebt);

    void deleteDebtorDebt(DebtorDebt debtorDebt);

    void updateDebtorDebtStatus(DebtorDebt debtorDebt, String debtorDebtStatus);
}
