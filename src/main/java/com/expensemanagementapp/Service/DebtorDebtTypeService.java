package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.DebtorDebtType;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.DebtorDebtStatusTypeModel;

import java.util.List;

public interface DebtorDebtTypeService {
    List<DebtorDebtType> findAllTypesAsc();

    void addDebtorDebtType(DebtorDebtStatusTypeModel debtorDebtStatusTypeModel, User user);

    DebtorDebtType getDebtorDebtTypeById(String debtorDebtTypeId);

    void updateDebtorDebtType(DebtorDebtType debtorDebtType, DebtorDebtStatusTypeModel debtorDebtStatusTypeModel);

    void deleteDebtorDebtType(DebtorDebtType debtorDebtType);
}
