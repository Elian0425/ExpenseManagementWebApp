package com.expensemanagementapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_gender")
public class UserGender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_gender_id", nullable = false)
    private Integer userGenderId;
    @Column(name = "user_gender_name", nullable = false)
    private String userGenderName;
}
