package com.github.joaog250.todospringgraphql.service;

import java.util.Optional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.security.JWTUserDetails;

public interface IAuthService {
    String getToken(User user);

    Optional<User> getCurrentUser();

    JWTUserDetails loadUserByToken(String token) throws JWTVerificationException;

}
