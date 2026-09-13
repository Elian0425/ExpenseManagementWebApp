package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.DebtorDebtType;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.DebtorDebtStatusTypeModel;
import com.expensemanagementapp.Repository.DebtorDebtTypeRepository;
import com.expensemanagementapp.Service.DebtorDebtTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtorDebtTypeServiceImpl implements DebtorDebtTypeService {

    @Autowired
    private DebtorDebtTypeRepository  debtorDebtTypeRepository;

    @Override
    public List<DebtorDebtType> findAllTypesAsc() {
        return debtorDebtTypeRepository.findAllByOrderByDebtorDebtTypeNameAsc();
    }

    @Override
    public void addDebtorDebtType(DebtorDebtStatusTypeModel debtorDebtStatusTypeModel, User user) {
        DebtorDebtType debtorDebtType = DebtorDebtType.builder()
                .debtorDebtTypeName(debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName())
                .debtorDebtTypePlacedOnDate(LocalDate.now())
                .user(user)
                .build();

        debtorDebtTypeRepository.save(debtorDebtType);
    }

    @Override
    public DebtorDebtType getDebtorDebtTypeById(String debtorDebtTypeId) {
        return debtorDebtTypeRepository.findById(Integer.valueOf(debtorDebtTypeId)).orElse(null);
    }

    @Override
    public void updateDebtorDebtType(DebtorDebtType debtorDebtType, DebtorDebtStatusTypeModel debtorDebtStatusTypeModel) {
        if (!debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName().isEmpty() &&
                !debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName().equals(debtorDebtType.getDebtorDebtTypeName())) {
            debtorDebtType.setDebtorDebtTypeName(debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName());
            debtorDebtType.setDebtorDebtTypeUpdatedOnDate(LocalDate.now());
        }

        debtorDebtTypeRepository.save(debtorDebtType);
    }

    @Override
    public void deleteDebtorDebtType(DebtorDebtType debtorDebtType) {
        debtorDebtTypeRepository.delete(debtorDebtType);
    }
}
