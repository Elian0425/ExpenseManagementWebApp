package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Entity.DebtorDebtPayment;
import com.expensemanagementapp.Model.DebtorDebtPaymentModel;
import com.expensemanagementapp.Repository.DebtorDebtPaymentRepository;
import com.expensemanagementapp.Service.DebtorDebtPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class DebtorDebtPaymentServiceImpl implements DebtorDebtPaymentService {

    @Autowired
    private DebtorDebtPaymentRepository debtorDebtPaymentRepository;

    @Override
    public List<DebtorDebtPayment> findAllDebtorDebtPaymentByDebtorDebt(DebtorDebt debtorDebt) {
        return debtorDebtPaymentRepository.findAllDebtorDebtPaymentByDebtorDebt(debtorDebt);
    }

    @Override
    public void addDebtorDebtPayment(DebtorDebt debtorDebt, DebtorDebtPaymentModel debtorDebtPaymentModel) {
        DebtorDebtPayment debtorDebtPayment = DebtorDebtPayment.builder()
                .debtorDebtPaymentAmount(debtorDebtPaymentModel.getDebtorDebtPaymentAmount())
                .debtorDebtPaymentPlacedOnDate(LocalDate.now())
                .debtorDebtPaymentDescription(debtorDebtPaymentModel.getDebtorDebtDescription())
                .debtorDebt(debtorDebt)
                .build();

        debtorDebtPaymentRepository.save(debtorDebtPayment);
    }

    @Override
    public DebtorDebtPayment findDebtorDebtPaymentById(int debtorDebtPaymentId) {
        return debtorDebtPaymentRepository.findById(debtorDebtPaymentId).orElse(null);
    }

    @Override
    public void updateDebtorDebtPayment(DebtorDebtPaymentModel debtorDebtPaymentModel, DebtorDebtPayment debtorDebtPayment) {

        if (debtorDebtPaymentModel.getDebtorDebtPaymentAmount() != debtorDebtPayment.getDebtorDebtPaymentAmount()){
            debtorDebtPayment.setDebtorDebtPaymentAmount(debtorDebtPaymentModel.getDebtorDebtPaymentAmount());
            debtorDebtPayment.setDebtorDebtPaymentUpdatedOnDate(LocalDate.now());
        }

        if (!debtorDebtPaymentModel.getDebtorDebtDescription().isEmpty() &&
                !debtorDebtPaymentModel.getDebtorDebtDescription().equals(debtorDebtPayment.getDebtorDebtPaymentDescription())){
            debtorDebtPayment.setDebtorDebtPaymentDescription(debtorDebtPaymentModel.getDebtorDebtDescription());
            debtorDebtPayment.setDebtorDebtPaymentUpdatedOnDate(LocalDate.now());
        }

        debtorDebtPaymentRepository.save(debtorDebtPayment);
    }

    @Override
    public void deleteDebtorDebtPayment(DebtorDebtPayment debtorDebtPayment) {
        debtorDebtPaymentRepository.delete(debtorDebtPayment);
    }

}
