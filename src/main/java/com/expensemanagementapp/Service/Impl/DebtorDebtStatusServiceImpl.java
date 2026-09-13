package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.DebtorDebtStatus;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.DebtorDebtStatusTypeModel;
import com.expensemanagementapp.Repository.DebtorDebtStatusRepository;
import com.expensemanagementapp.Service.DebtorDebtStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtorDebtStatusServiceImpl implements DebtorDebtStatusService {

    @Autowired
    private DebtorDebtStatusRepository  debtorDebtStatusRepository;

    @Override
    public List<DebtorDebtStatus> findAllStatusesAsc() {
        return debtorDebtStatusRepository.findAllByOrderByDebtorDebtStatusNameAsc();
    }

    @Override
    public void addDebtorDebtStatus(DebtorDebtStatusTypeModel debtorDebtStatusTypeModel, User user) {
        DebtorDebtStatus debtorDebtStatus = DebtorDebtStatus.builder()
                .debtorDebtStatusName(debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName())
                .debtorDebtStatusPlacedOnDate(LocalDate.now())
                .user(user)
                .build();

        debtorDebtStatusRepository.save(debtorDebtStatus);
    }

    @Override
    public DebtorDebtStatus findDebtorDebtStatusById(String debtorDebtStatusId) {
        return debtorDebtStatusRepository.findById(Integer.valueOf(debtorDebtStatusId)).orElse(null);
    }

    @Override
    public void updateDebtorDebtStatus(DebtorDebtStatus debtorDebtStatus, DebtorDebtStatusTypeModel debtorDebtStatusTypeModel) {
        if (!debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName().isEmpty() &&
                !debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName().equals(debtorDebtStatus.getDebtorDebtStatusName())) {
            debtorDebtStatus.setDebtorDebtStatusName(debtorDebtStatusTypeModel.getDebtorDebtStatusTypeName());
            debtorDebtStatus.setDebtorDebtStatusUpdatedOnDate(LocalDate.now());
        }

        debtorDebtStatusRepository.save(debtorDebtStatus);
    }

    @Override
    public void deleteDebtorDebtStatus(DebtorDebtStatus debtorDebtStatus) {
        debtorDebtStatusRepository.delete(debtorDebtStatus);
    }
}
