package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserGender;
import com.expensemanagementapp.Model.UserModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public interface UserService {
    List<UserGender> getAllUserGenderAsc();

    void accountActivation(User user);

    User getUserByFirstnameAndLastnameAndDOBAndPhoneNumber(
            String userFirstName,
            String userLastName,
            String userDOB,
            String userPhoneNumber
    );

    User getUserByFirstnameAndLastnameAndDOBAndEmailAddress(
            String userFirstName,
            String userLastName,
            String userDOB,
            String userEmail
    );

    User getUserByEmail(String userEmail);

    void userPasswordReset(User user, UserModel userModel);

    void userEmailUpdate(User user, String userNewEmail);
}
