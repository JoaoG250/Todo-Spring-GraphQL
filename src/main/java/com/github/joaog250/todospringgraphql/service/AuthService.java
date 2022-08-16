package com.github.joaog250.todospringgraphql.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.repository.UserRepository;
import com.github.joaog250.todospringgraphql.security.JWTUserDetails;

@Service("jwtService")
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final JWTVerifier accessTokenVerifier;
    private final JWTVerifier refreshTokenVerifier;
    private final Algorithm accessTokenAlgorithm;
    private final Algorithm refreshTokenAlgorithm;
    private final static String issuer = "todo-spring-graphql";

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.accessTokenAlgorithm = Algorithm.HMAC256("accessTokenSecret");
        this.refreshTokenAlgorithm = Algorithm.HMAC256("refreshTokenSecret");
        this.accessTokenVerifier = JWT.require(accessTokenAlgorithm).withIssuer(issuer).build();
        this.refreshTokenVerifier = JWT.require(refreshTokenAlgorithm).withIssuer(issuer).build();
    }

    private String signToken(String subject, Instant expiry, Algorithm algorithm) {
        Instant now = Instant.now();
        return JWT
                .create()
                .withIssuer(issuer)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(subject)
                .sign(algorithm);
    }

    private Optional<DecodedJWT> verifyToken(String token, JWTVerifier verifier) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    @Override
    public String signAccessToken(User user) {
        Instant expiry = Instant.now().plus(Duration.ofHours(2)); // Token will be valid for 2 hours
        return signToken(user.getEmail(), expiry, accessTokenAlgorithm);
    }

    @Override
    public String signRefreshToken(User user) {
        Instant expiry = Instant.now().plus(Duration.ofDays(1)); // Token will be valid for 2 hours
        return signToken(user.getEmail(), expiry, refreshTokenAlgorithm);
    }

    @Override
    public String refreshToken(String refreshToken) throws JWTVerificationException {
        return verifyToken(refreshToken, refreshTokenVerifier)
                .map(DecodedJWT::getSubject)
                .flatMap(userRepository::findByEmail)
                .map(user -> signAccessToken(user))
                .orElseThrow(() -> new JWTVerificationException("Invalid token"));
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findByEmail);
    }

    @Override
    public JWTUserDetails loadUserByToken(String token) throws JWTVerificationException {
        return verifyToken(token, accessTokenVerifier)
                .map(DecodedJWT::getSubject)
                .flatMap(userRepository::findByEmail)
                .map(user -> getUserDetails(user, token))
                .orElseThrow(() -> new JWTVerificationException("Invalid token"));
    }

    private JWTUserDetails getUserDetails(User user, String token) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return JWTUserDetails
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .token(token)
                .build();
    }

}
