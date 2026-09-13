package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Integer> {
    @Query("SELECT uvt FROM UserVerificationToken uvt JOIN FETCH uvt.user WHERE uvt.userVerificationToken = :token")
    UserVerificationToken findByUserVerificationToken(@Param("token") String token);
}
