package com.github.joaog250.todospringgraphql.controller;

import java.util.List;

import org.springframework.data.web.ProjectedPayload;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.github.joaog250.todospringgraphql.dto.TodoDto;
import com.github.joaog250.todospringgraphql.model.Todo;
import com.github.joaog250.todospringgraphql.service.ITodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final ITodoService todoService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Todo todo(@Argument String id) {
        return todoService.getTodoById(id);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<Todo> todos() {
        return todoService.getAllTodos();
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Todo saveTodo(@Argument String id, @Argument TodoInput data) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(id);
        todoDto.setTitle(data.getTitle());
        todoDto.setDescription(data.getDescription());
        todoDto.setDone(data.isDone());
        return todoService.saveTodo(todoDto);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public boolean deleteTodo(@Argument String id) {
        todoService.deleteTodo(id);
        return true;
    }
}

@ProjectedPayload
interface TodoInput {
    String getTitle();

    String getDescription();

    boolean isDone();
}
