package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Entity.DebtorDebtPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorDebtPaymentRepository extends JpaRepository<DebtorDebtPayment, Integer> {
    List<DebtorDebtPayment> findAllDebtorDebtPaymentByDebtorDebt(DebtorDebt debtorDebt);
}
