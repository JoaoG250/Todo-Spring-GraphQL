package com.github.joaog250.todospringgraphql.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.security.JWTUserDetails;

public interface IAuthService {
    String getToken(User user);

    User getCurrentUser();

    JWTUserDetails loadUserByToken(String token) throws JWTVerificationException;

}
