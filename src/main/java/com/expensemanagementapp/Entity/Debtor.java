package com.expensemanagementapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "debtor")
public class Debtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtor_id", nullable = false)
    private Integer debtorId;
    @Column(name = "debtor_first_name", nullable = false)
    private String debtorFirstName;
    @Column(name = "debtor_last_name", nullable = false)
    private String debtorLastName;
    @Column(name = "debtor_email", nullable = false)
    private String debtorEmail;
    @Column(name = "debtor_phone_number")
    private String debtorPhoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_debtor_user"))
    private User user;

    @OneToMany(mappedBy = "debtor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebtorDebt> debtorDebtList;
}
