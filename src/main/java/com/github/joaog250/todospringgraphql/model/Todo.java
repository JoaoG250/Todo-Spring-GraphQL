package com.github.joaog250.todospringgraphql.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Todo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
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

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
