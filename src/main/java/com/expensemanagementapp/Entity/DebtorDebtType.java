package com.expensemanagementapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "debtor_debt_type")
public class DebtorDebtType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtor_debt_type_id", nullable = false)
    private Integer debtorDebtTypeId;
    @Column(name = "debtor_debt_type_name", nullable = false)
    private String debtorDebtTypeName;
    @Column(name = "debtor_debt_type_placed_on_date", nullable = false)
    private LocalDate debtorDebtTypePlacedOnDate;
    @Column(name = "debtor_debt_Type_updated_on_date")
    private LocalDate debtorDebtTypeUpdatedOnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_debtor_debt_type_user"))
    private User user;
}
