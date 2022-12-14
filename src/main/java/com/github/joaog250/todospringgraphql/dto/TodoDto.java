package com.github.joaog250.todospringgraphql.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDto {
    private String id;
    private String title;
    private String description;
    private boolean done;
}
