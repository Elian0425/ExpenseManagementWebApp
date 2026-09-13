package com.expensemanagementapp.Service.Impl;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserVerificationToken;
import com.expensemanagementapp.Repository.UserVerificationTokenRepository;
import com.expensemanagementapp.Service.UserVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserVerificationTokenServiceImpl implements UserVerificationTokenService {

    @Autowired
    private UserVerificationTokenRepository userVerificationTokenRepository;

    @Override
    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();

        UserVerificationToken userVerificationToken = UserVerificationToken.builder()
                .userVerificationToken(token)
                .userVerificationTokenCreatedOn(Instant.now())
                .userVerificationTokenValid(true)
                .user(user)
                .build();

        userVerificationTokenRepository.save(userVerificationToken);

        return userVerificationToken.getUserVerificationToken();
    }

    @Override
    public String generateToken(User user, String userNewEmail) {
        String token = UUID.randomUUID().toString();

        UserVerificationToken userVerificationToken = UserVerificationToken.builder()
                .userVerificationToken(token)
                .userVerificationTokenCreatedOn(Instant.now())
                .userVerificationTokenValid(true)
                .userNewEmail(userNewEmail)
                .user(user)
                .build();

        userVerificationTokenRepository.save(userVerificationToken);

        return userVerificationToken.getUserVerificationToken();
    }

    @Override
    public UserVerificationToken getToken(String token) {
        return userVerificationTokenRepository.findByUserVerificationToken(token);
    }

    @Override
    public boolean isTokenValid(UserVerificationToken token) {
        UserVerificationToken userVerificationToken = userVerificationTokenRepository.findByUserVerificationToken(token.getUserVerificationToken());
        Instant expirationTime = userVerificationToken.getUserVerificationTokenCreatedOn().plusSeconds(900);
        return Instant.now().isBefore(expirationTime);
    }
}
