package com.expensemanagementapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "debtor_debt_payment")
public class DebtorDebtPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtor_debt_payment_id", nullable = false)
    private Integer debtorDebtPaymentId;
    @Column(name = "debtor_debt_payment_amount", nullable = false)
    private double debtorDebtPaymentAmount;
    @Column(name = "debtor_debt_payment_placed_on_date", nullable = false)
    private LocalDate debtorDebtPaymentPlacedOnDate;
    @Column(name = "debtor_Debt_payment_due_date")
    private LocalDate debtorDebtPaymentUpdatedOnDate;
    @Column(name = "debtor_debt_payment_description")
    private String debtorDebtPaymentDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "debtor_debt_id", nullable = false, foreignKey = @ForeignKey(name = "FK_debtor_debt_payment_debtor_debt"))
    private DebtorDebt debtorDebt;

}
