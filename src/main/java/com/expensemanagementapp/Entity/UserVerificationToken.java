package com.expensemanagementapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_verification_token")
public class UserVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_verification_token_id", nullable = false)
    private Integer userVerificationTokenId;
    @Column(name = "user_verification_token", nullable = false)
    private String userVerificationToken;
    @Column(name = "user_verification_token_created_on", nullable = false)
    private Instant userVerificationTokenCreatedOn;
    @Column(name = "user_verification_token_validation", nullable = false)
    private boolean userVerificationTokenValid;
    @Column(name = "user_new_email")
    private String userNewEmail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_user_verification_token_user"))
    private User user;
}
