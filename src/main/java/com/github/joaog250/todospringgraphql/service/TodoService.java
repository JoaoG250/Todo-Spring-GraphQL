package com.github.joaog250.todospringgraphql.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.github.joaog250.todospringgraphql.dto.TodoDto;
import com.github.joaog250.todospringgraphql.model.Todo;
import com.github.joaog250.todospringgraphql.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service("todoService")
@RequiredArgsConstructor
public class TodoService implements ITodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> getTodoById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    public Todo saveTodo(TodoDto todo) {
        Todo newTodo = Todo.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .done(todo.isDone())
                .build();
        return todoRepository.save(newTodo);
    }

    @Override
    public void deleteTodo(String id) throws EntityNotFoundException {
        getTodoById(id).ifPresentOrElse(todo -> todoRepository.delete(todo), () -> {
            throw new EntityNotFoundException("Todo not found");
        });
    }
}
