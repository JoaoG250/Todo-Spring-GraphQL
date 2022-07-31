package com.github.joaog250.todospringgraphql;

import java.util.Arrays;
import java.util.List;

public class Todo {

    private String id;
    private String title;
    private String description;
    private boolean done;

    public Todo(String id, String title, String description, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    private static List<Todo> todos = Arrays.asList(
            new Todo("todo-1", "Learn GraphQL", "Learn GraphQL", false),
            new Todo("todo-2", "Learn Spring Boot", "Learn Spring Boot", false),
            new Todo("todo-3", "Learn Spring Data", "Learn Spring Data", false),
            new Todo("todo-4", "Learn Spring Security", "Learn Spring Security", false));

    public static Todo getById(String id) {
        return todos.stream().filter(todo -> todo.getId().equals(id)).findFirst().orElse(null);
    }

    public String getId() {
        return id;
    }
}
