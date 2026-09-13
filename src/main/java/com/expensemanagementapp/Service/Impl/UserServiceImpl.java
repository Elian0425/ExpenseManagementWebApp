package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserGender;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Repository.UserGenderRepository;
import com.expensemanagementapp.Repository.UserRepository;
import com.expensemanagementapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserGenderRepository userGenderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserGender> getAllUserGenderAsc() {
        return userGenderRepository.findAllByOrderByUserGenderNameAsc();
    }

    @Override
    public void accountActivation(User user) {
        user.setUserEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User getUserByFirstnameAndLastnameAndDOBAndPhoneNumber(String userFirstName, String userLastName, String userDOB, String userPhoneNumber) {

        return userRepository.findByUserFirstNameAndUserLastNameAndUserDOBAndUserPhoneNumber(
                        userFirstName, userLastName, userDOB, userPhoneNumber
                ).orElse(null);
    }

    @Override
    public User getUserByFirstnameAndLastnameAndDOBAndEmailAddress(String userFirstName, String userLastName, String userDOB, String userEmail) {
        return userRepository.findByUserFirstNameAndUserLastNameAndUserDOBAndUserEmail(
                userFirstName, userLastName, userDOB, userEmail
        ).orElse(null);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElse(null);
    }

    @Override
    public void userPasswordReset(User user, UserModel userModel) {
        user.setUserPassword(passwordEncoder.encode(userModel.getUserPassword()));
        userRepository.save(user);
    }

    @Override
    public void userEmailUpdate(User user, String userNewEmail) {
        user.setUserEmail(userNewEmail);
        userRepository.save(user);
    }
}
