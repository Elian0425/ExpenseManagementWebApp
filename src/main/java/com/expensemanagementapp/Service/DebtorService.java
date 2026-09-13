package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.UserModel;

import java.util.List;

public interface DebtorService {

    List<Debtor> getAllDebtorsDesc();

    void addDebtor(UserModel userModel, User user);

    Debtor findDebtorById(int debtorId);

    void updateDebtor(Debtor debtor, UserModel userModel);

    void deleteDebtor(Debtor debtor);

}
