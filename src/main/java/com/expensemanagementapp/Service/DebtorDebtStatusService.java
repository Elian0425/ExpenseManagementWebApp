package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.DebtorDebtStatus;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.DebtorDebtStatusTypeModel;

import java.util.List;

public interface DebtorDebtStatusService {
    List<DebtorDebtStatus> findAllStatusesAsc();

    void addDebtorDebtStatus(DebtorDebtStatusTypeModel debtorDebtStatusTypeModel, User user);

    DebtorDebtStatus findDebtorDebtStatusById(String debtorDebtStatusId);

    void updateDebtorDebtStatus(DebtorDebtStatus debtorDebtStatus, DebtorDebtStatusTypeModel debtorDebtStatusTypeModel);

    void deleteDebtorDebtStatus(DebtorDebtStatus debtorDebtStatus);
}
