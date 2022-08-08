package com.github.joaog250.todospringgraphql.controller;

import java.util.List;

import org.springframework.data.web.ProjectedPayload;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.github.joaog250.todospringgraphql.dto.UserDto;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.service.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @QueryMapping
    public User user(@Argument String id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public List<User> users() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public User saveUser(@Argument String id, @Argument UserInput data) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(data.getName());
        userDto.setEmail(data.getEmail());
        userDto.setPassword(data.getPassword());
        return userService.saveUser(userDto);
    }

    @MutationMapping
    public boolean deleteUser(@Argument String id) {
        userService.deleteUser(id);
        return true;
    }
}

@ProjectedPayload
interface UserInput {
    String getName();

    String getEmail();

    String getPassword();
}
