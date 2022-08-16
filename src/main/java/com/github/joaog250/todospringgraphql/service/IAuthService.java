package com.github.joaog250.todospringgraphql.service;

import java.util.Optional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.security.JWTUserDetails;

public interface IAuthService {
    String signAccessToken(User user);

    String signRefreshToken(User user);

    String refreshToken(String token) throws JWTVerificationException;

    Optional<User> getCurrentUser();

    JWTUserDetails loadUserByToken(String token) throws JWTVerificationException;

}
