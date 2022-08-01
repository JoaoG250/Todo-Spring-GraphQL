package com.github.joaog250.todospringgraphql.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.web.ProjectedPayload;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.github.joaog250.todospringgraphql.dto.TodoDto;
import com.github.joaog250.todospringgraphql.model.Todo;
import com.github.joaog250.todospringgraphql.service.ITodoService;

@Controller
public class TodoController {

    @Resource(name = "todoService")
    private ITodoService todoService;

    @QueryMapping
    public Todo todo(@Argument String id) {
        return todoService.getTodoById(id);
    }

    @QueryMapping
    public List<Todo> todos() {
        return todoService.getAllTodos();
    }

    @MutationMapping
    public Todo saveTodo(@Argument String id, @Argument TodoInput data) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(id);
        todoDto.setTitle(data.getTitle());
        todoDto.setDescription(data.getDescription());
        todoDto.setDone(data.getDone());
        return todoService.saveTodo(todoDto);
    }

    @MutationMapping
    public boolean deleteTodo(@Argument String id) {
        todoService.deleteTodo(id);
        return true;
    }
}

@ProjectedPayload
interface TodoInput {
    String getTitle();

    String getDescription();

    boolean getDone();
}
