package com.github.joaog250.todospringgraphql.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.joaog250.todospringgraphql.dto.RoleDto;
import com.github.joaog250.todospringgraphql.dto.UserDto;
import com.github.joaog250.todospringgraphql.model.Role;
import com.github.joaog250.todospringgraphql.model.User;

public interface IUserService extends UserDetailsService {
    List<User> getAllUsers();

    User getUserById(String id);

    User getUserByEmail(String email);

    User saveUser(UserDto user);

    void deleteUser(String id);

    Role saveRole(RoleDto role);

    void addRoleToUser(String email, String roleName);
}
