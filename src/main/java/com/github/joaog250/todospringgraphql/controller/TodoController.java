package com.github.joaog250.todospringgraphql.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.github.joaog250.todospringgraphql.model.Todo;

@Controller
public class TodoController {
    @QueryMapping
    public Todo todo(@Argument String id) {
        return Todo.getById(id);
    }
}
