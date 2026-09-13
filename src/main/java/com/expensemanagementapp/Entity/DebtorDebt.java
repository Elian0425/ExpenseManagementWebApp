package com.expensemanagementapp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "debtor_debt")
public class DebtorDebt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtor_debt_id", nullable = false)
    private Integer debtorDebtId;
    @Column(name = "debtor_debt_name", nullable = false)
    private String debtorDebtName;
    @Column(name = "debtor_debt_amount", nullable = false)
    private double debtorDebtAmount;
    @Column(name = "debtor_debt_placed_on_date", nullable = false)
    private LocalDate debtorDebtPlacedOnDate;
    @Column(name = "debtor_debt_due_date")
    private LocalDate debtorDebtDueDate;
    @Column(name = "debtor_debt_status", nullable = false)
    private String debtorDebtStatus;
    @Column(name = "debtor_debt_type", nullable = false)
    private String debtorDebtType;
    @Column(name = "debtor_debt_description")
    private String debtorDebtDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "debtor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_debtor_debt_debtor"))
    private Debtor debtor;

    @OneToMany(mappedBy = "debtorDebt", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebtorDebtPayment> debtorDebtPaymentList;


}
