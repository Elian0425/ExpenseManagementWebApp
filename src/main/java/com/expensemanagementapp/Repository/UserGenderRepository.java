package com.expensemanagementapp.Repository;

import com.expensemanagementapp.Entity.UserGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGenderRepository extends JpaRepository<UserGender, Integer> {
    List<UserGender> findAllByOrderByUserGenderNameAsc();

    boolean existsUserGenderByUserGenderName(String name);
}
