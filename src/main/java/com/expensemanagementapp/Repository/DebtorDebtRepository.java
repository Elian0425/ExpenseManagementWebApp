package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.DebtorDebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorDebtRepository extends JpaRepository<DebtorDebt, Integer> {
    List<DebtorDebt> findAllByDebtorDebtorId(Integer debtorDebtId);
}
