package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Repository.DebtorRepository;
import com.expensemanagementapp.Service.DebtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtorServiceImpl implements DebtorService {

    @Autowired
    private DebtorRepository debtorRepository;

    @Override
    public List<Debtor> getAllDebtorsDesc() {
        return debtorRepository.findAllByOrderByDebtorFirstNameDesc();
    }

    @Override
    public void addDebtor(UserModel userModel, User user) {
        Debtor debtor = Debtor.builder()
                .debtorFirstName(userModel.getUserFirstName())
                .debtorLastName(userModel.getUserLastName())
                .debtorEmail(userModel.getUserEmail())
                .debtorPhoneNumber(userModel.getUserPhoneNumber())
                .user(user)
                .build();

        debtorRepository.save(debtor);
    }

    @Override
    public Debtor findDebtorById(int debtorId) {
        return debtorRepository.findById((debtorId)).orElse(null);
    }

    @Override
    public void updateDebtor(Debtor debtor, UserModel userModel) {
        if (!userModel.getUserFirstName().isEmpty() &&
                !debtor.getDebtorFirstName().equals(userModel.getUserFirstName())){
            debtor.setDebtorFirstName(userModel.getUserFirstName());
        }

        if (!userModel.getUserLastName().isEmpty() &&
                !debtor.getDebtorLastName().equals(userModel.getUserLastName())){
            debtor.setDebtorLastName(userModel.getUserLastName());
        }

        if (!userModel.getUserEmail().isEmpty() &&
                !debtor.getDebtorEmail().equals(userModel.getUserEmail())){
            debtor.setDebtorEmail(userModel.getUserEmail());
        }

        if (!userModel.getUserPhoneNumber().isEmpty() &&
                !debtor.getDebtorPhoneNumber().equals(userModel.getUserPhoneNumber())){
            debtor.setDebtorPhoneNumber(userModel.getUserPhoneNumber());
        }

        debtorRepository.save(debtor);

    }

    @Override
    public void deleteDebtor(Debtor debtor) {
        debtorRepository.delete(debtor);
    }
}
