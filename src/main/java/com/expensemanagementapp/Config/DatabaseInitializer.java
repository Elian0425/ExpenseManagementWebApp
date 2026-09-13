package com.expensemanagementapp.Config;


import com.expensemanagementapp.Entity.UserGender;
import com.expensemanagementapp.Repository.UserGenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer {

    @Autowired
    private UserGenderRepository userGenderRepository;

    @Bean
    CommandLineRunner initializeDatabase() {

        return args -> {

            List<String> genders = List.of(
                    "Male",
                    "Female",
                    "Other"

            );

            genders.forEach(genderName -> {

                if (!userGenderRepository.existsUserGenderByUserGenderName(genderName)) {
                    UserGender userGender = UserGender.builder()
                            .userGenderName(genderName)
                            .build();
                    userGenderRepository.save(userGender);
                }

            });
        };
    }
}
