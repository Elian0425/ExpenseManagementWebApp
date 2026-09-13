package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserFirstNameAndUserLastNameAndUserDOBAndUserPhoneNumber(String userFirstName, String userLastName, String userDOB, String userPhoneNumber);

    Optional<User> findByUserFirstNameAndUserLastNameAndUserDOBAndUserEmail(String userFirstName, String userLastName, String userDOB, String userEmail);
}
