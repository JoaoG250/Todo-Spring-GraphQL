package com.github.joaog250.todospringgraphql.service;

import java.util.List;

import com.github.joaog250.todospringgraphql.model.Role;
import com.github.joaog250.todospringgraphql.model.User;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(String id);

    User getUserByEmail(String email);

    User saveUser(User user);

    void deleteUser(String id);

    Role saveRole(Role role);

    void addRoleToUser(String email, String roleName);

}
