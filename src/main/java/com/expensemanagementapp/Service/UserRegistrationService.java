package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.UserModel;

public interface UserRegistrationService {
    User registerUser(UserModel userModel);
}
