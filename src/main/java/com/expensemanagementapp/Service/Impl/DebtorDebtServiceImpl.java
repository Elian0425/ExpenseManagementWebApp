package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Model.DebtorDebtModel;
import com.expensemanagementapp.Repository.DebtorDebtRepository;
import com.expensemanagementapp.Service.DebtorDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DebtorDebtServiceImpl implements DebtorDebtService {

    @Autowired
    private DebtorDebtRepository debtorDebtRepository;

    @Override
    public List<DebtorDebt> findAllDebtorDebtsByDebtorId(int debtorDebtId) {
        return debtorDebtRepository.findAllByDebtorDebtorId(debtorDebtId);
    }

    @Override
    public DebtorDebt findDebtorDebtById(int debtorDebtId) {
        return debtorDebtRepository.findById(debtorDebtId).orElse(null);
    }


    @Override
    public void addDebtorDebt(DebtorDebtModel debtorDebtModel, Debtor debtor) {

        DebtorDebt debtorDebt = DebtorDebt.builder()
                .debtorDebtName(debtorDebtModel.getDebtorDebtName())
                .debtorDebtAmount(debtorDebtModel.getDebtorDebtAmount())
                .debtorDebtPlacedOnDate(LocalDate.now())
                .debtorDebtDueDate(LocalDate.parse(debtorDebtModel.getDebtorDebtDueDate()))
                .debtorDebtStatus("Unpaid")
                .debtorDebtType(debtorDebtModel.getDebtorDebtType())
                .debtorDebtDescription(debtorDebtModel.getDebtorDebtDescription())
                .debtor(debtor)
                .build();

        debtorDebtRepository.save(debtorDebt);
    }

    @Override
    public void updateDebtorDebt(DebtorDebtModel debtorDebtModel, DebtorDebt debtorDebt) {

        if (!debtorDebtModel.getDebtorDebtName().isEmpty() &&
                !debtorDebtModel.getDebtorDebtName().equals(debtorDebt.getDebtorDebtName())) {
            debtorDebt.setDebtorDebtName(debtorDebtModel.getDebtorDebtName());
        }

        if (debtorDebtModel.getDebtorDebtAmount() >= 0.01 &&
                debtorDebtModel.getDebtorDebtAmount() != debtorDebt.getDebtorDebtAmount()) {
            debtorDebt.setDebtorDebtAmount(debtorDebtModel.getDebtorDebtAmount());
        }

        if (!debtorDebtModel.getDebtorDebtDueDate().isEmpty() &&
                !debtorDebtModel.getDebtorDebtDueDate().equals(String.valueOf(debtorDebt.getDebtorDebtDueDate()))) {
            debtorDebt.setDebtorDebtDueDate(LocalDate.parse(debtorDebtModel.getDebtorDebtDueDate()));
        }

        if (!debtorDebtModel.getDebtorDebtStatus().isEmpty() &&
                !debtorDebtModel.getDebtorDebtStatus().equals("default") &&
                !debtorDebtModel.getDebtorDebtStatus().equals(debtorDebt.getDebtorDebtStatus())) {
            debtorDebt.setDebtorDebtStatus(debtorDebtModel.getDebtorDebtStatus());
        }

        if (!debtorDebtModel.getDebtorDebtType().isEmpty() &&
                !debtorDebtModel.getDebtorDebtType().equals("default") &&
                !debtorDebtModel.getDebtorDebtType().equals(debtorDebt.getDebtorDebtType())) {
            debtorDebt.setDebtorDebtType(debtorDebtModel.getDebtorDebtType());
        }

        if (!debtorDebt.getDebtorDebtDescription().equals(debtorDebtModel.getDebtorDebtDescription())) {
            debtorDebt.setDebtorDebtDescription(debtorDebtModel.getDebtorDebtDescription());
        }

        debtorDebtRepository.save(debtorDebt);
    }

    @Override
    public void deleteDebtorDebt(DebtorDebt debtorDebt) {
        debtorDebtRepository.delete(debtorDebt);
    }

    @Override
    public void updateDebtorDebtStatus(DebtorDebt debtorDebt, String debtorDebtStatus) {
        debtorDebt.setDebtorDebtStatus(debtorDebtStatus);
        debtorDebtRepository.save(debtorDebt);
    }

}
