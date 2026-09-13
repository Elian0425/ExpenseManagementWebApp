package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.DebtorDebtType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorDebtTypeRepository extends JpaRepository<DebtorDebtType, Integer> {
    List<DebtorDebtType> findAllByOrderByDebtorDebtTypeNameAsc();
}
