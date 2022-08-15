package com.github.joaog250.todospringgraphql.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.joaog250.todospringgraphql.dto.RoleDto;
import com.github.joaog250.todospringgraphql.dto.UserDto;
import com.github.joaog250.todospringgraphql.model.Role;
import com.github.joaog250.todospringgraphql.model.User;

public interface IUserService extends UserDetailsService {
    List<User> getAllUsers();

    Optional<User> getUserById(String id);

    Optional<User> getUserByEmail(String email);

    User saveUser(UserDto user);

    void deleteUser(String id) throws EntityNotFoundException;

    Role saveRole(RoleDto role);

    void addRoleToUser(String email, String roleName) throws EntityNotFoundException;
}
