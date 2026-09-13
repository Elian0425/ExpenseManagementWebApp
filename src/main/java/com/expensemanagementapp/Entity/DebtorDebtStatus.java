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
@Table(name = "debtor_debt_status")
public class DebtorDebtStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtor_debt_status_id", nullable = false)
    private Integer debtorDebtStatusId;
    @Column(name = "debtor_debt_status_name", nullable = false)
    private String debtorDebtStatusName;
    @Column(name = "debtor_debt_status_placed_on_date", nullable = false)
    private LocalDate debtorDebtStatusPlacedOnDate;
    @Column(name = "debtor_debt_status_updated_on_date")
    private LocalDate debtorDebtStatusUpdatedOnDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_debtor_debt_status_user"))
    private User user;
}
