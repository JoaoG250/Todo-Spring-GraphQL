package com.github.joaog250.todospringgraphql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.joaog250.todospringgraphql.dto.RoleDto;
import com.github.joaog250.todospringgraphql.dto.UserDto;
import com.github.joaog250.todospringgraphql.service.UserService;

@SpringBootApplication
@EnableJpaAuditing
public class TodoSpringGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoSpringGraphqlApplication.class, args);
	}

	@Bean
	public Algorithm jwtAlgorithm() {
		return Algorithm.HMAC256("secret");
	}

	@Bean
	public JWTVerifier jwtVerifier(Algorithm algorithm) {
		return JWT.require(algorithm).withIssuer("todo-spring-graphql").build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
