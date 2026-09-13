package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Enum.UserGender;
import com.expensemanagementapp.Enum.UserRole;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Repository.UserRepository;
import com.expensemanagementapp.Service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register user
    @Override
    public User registerUser(UserModel userModel) {

        // Build user
        User user = User.builder()
                .userFirstName(userModel.getUserFirstName())
                .userLastName(userModel.getUserLastName())
                .userDOB(LocalDate.parse(userModel.getUserDOB(),
                        DateTimeFormatter.ofPattern("MM-dd-yyyy")))
                .userGender(UserGender.valueOf(userModel.getUserGender()))
                .userPhoneNumber(userModel.getUserPhoneNumber())
                .userEmail(userModel.getUserEmail())
                .userPassword(passwordEncoder.encode(userModel.getUserPassword()))
                .userRole(UserRole.USER)
                .userEnabled(false)
                .build();

        // Save user
        userRepository.save(user);

        // Return user
        return user;
    }
}
