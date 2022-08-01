package com.github.joaog250.todospringgraphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.joaog250.todospringgraphql.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
}
