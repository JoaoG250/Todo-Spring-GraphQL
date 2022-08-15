package com.github.joaog250.todospringgraphql.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.joaog250.todospringgraphql.dto.RoleDto;
import com.github.joaog250.todospringgraphql.dto.UserDto;
import com.github.joaog250.todospringgraphql.model.Role;
import com.github.joaog250.todospringgraphql.model.User;
import com.github.joaog250.todospringgraphql.repository.RoleRepository;
import com.github.joaog250.todospringgraphql.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + email));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(UserDto user) {
        User newUser = User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(String id) throws EntityNotFoundException {
        getUserById(id).ifPresentOrElse(user -> userRepository.delete(user), () -> {
            throw new EntityNotFoundException("User not found");
        });
    }

    @Override
    public Role saveRole(RoleDto role) {
        Role newRole = Role.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
        return roleRepository.save(newRole);
    }

    @Override
    public void addRoleToUser(String email, String roleName) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(
                () -> new EntityNotFoundException("Role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

}
