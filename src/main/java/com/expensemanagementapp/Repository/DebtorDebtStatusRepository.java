package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.DebtorDebtStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorDebtStatusRepository extends JpaRepository<DebtorDebtStatus, Integer> {

    List<DebtorDebtStatus> findAllByOrderByDebtorDebtStatusNameAsc();
}
