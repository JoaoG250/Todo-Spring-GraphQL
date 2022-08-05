package com.github.joaog250.todospringgraphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodoSpringGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoSpringGraphqlApplication.class, args);
	}

}
