package com.github.joaog250.todospringgraphql.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.github.joaog250.todospringgraphql.dto.TodoDto;
import com.github.joaog250.todospringgraphql.model.Todo;

public interface ITodoService {
    Todo saveTodo(TodoDto todo);

    Optional<Todo> getTodoById(String id);

    List<Todo> getAllTodos();

    void deleteTodo(String id) throws EntityNotFoundException;
}
