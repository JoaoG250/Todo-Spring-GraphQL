package com.github.joaog250.todospringgraphql.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.joaog250.todospringgraphql.dto.TodoDto;
import com.github.joaog250.todospringgraphql.model.Todo;
import com.github.joaog250.todospringgraphql.repository.TodoRepository;

@Service("todoService")
public class TodoService implements ITodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoById(String id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Todo not found"));
    }

    @Override
    public Todo saveTodo(TodoDto todo) {
        Todo newTodo = buildTodo(todo);
        return todoRepository.save(newTodo);
    }

    @Override
    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }

    private Todo buildTodo(TodoDto todoDto) {
        Todo todo = new Todo(todoDto.getId(),
                todoDto.getTitle(),
                todoDto.getDescription(),
                todoDto.getDone());
        return todo;
    }
}
