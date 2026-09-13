package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Integer> {
    List<Debtor> findAllByOrderByDebtorFirstNameDesc();
}
