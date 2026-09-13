package com.expensemanagementapp.Service;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserVerificationToken;

public interface UserVerificationTokenService {
    String generateToken(User user);

    String generateToken(User user, String newEmail);

    UserVerificationToken getToken(String token);

    boolean isTokenValid(UserVerificationToken token);
}
