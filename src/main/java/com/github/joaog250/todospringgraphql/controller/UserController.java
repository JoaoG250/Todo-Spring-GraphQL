package com.github.joaog250.todospringgraphql.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.web.ProjectedPayload;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.github.joaog250.todospringgraphql.dto.RoleDto;
import com.github.joaog250.todospringgraphql.dto.UserDto;
import com.github.joaog250.todospringgraphql.model.Role;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.service.IAuthService;
import com.github.joaog250.todospringgraphql.service.IUserService;

import graphql.GraphQLException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IAuthService authService;
    private final AuthenticationProvider authenticationProvider;

    @MutationMapping
    @PreAuthorize("isAnonymous()")
    public String login(@Argument String email, @Argument String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationProvider.authenticate(credentials));
            Optional<User> user = authService.getCurrentUser();
            if (user.isPresent()) {
                return authService.getToken(user.get());
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User user(@Argument String id) {
        return userService.getUserById(id).orElse(null);
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> users() {
        return userService.getAllUsers();
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User saveUser(@Argument String id, @Argument UserInput data) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(data.getName());
        userDto.setEmail(data.getEmail());
        userDto.setPassword(data.getPassword());
        return userService.saveUser(userDto);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean deleteUser(@Argument String id) {
        try {
            userService.deleteUser(id);
            return true;
        } catch (EntityNotFoundException e) {
            throw new GraphQLException(e.getMessage());
        }
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Role saveRole(@Argument String id, @Argument RoleInput data) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleDto.setName(data.getName());
        return userService.saveRole(roleDto);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean addRoleToUser(@Argument String email, @Argument String roleName) {
        try {
            userService.addRoleToUser(email, roleName);
            return true;
        } catch (EntityNotFoundException e) {
            throw new GraphQLException(e.getMessage());
        }
    }
}

@ProjectedPayload
interface UserInput {
    String getName();

    String getEmail();

    String getPassword();
}

@ProjectedPayload
interface RoleInput {
    String getName();
}
