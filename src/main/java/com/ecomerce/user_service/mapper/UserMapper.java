package com.ecomerce.user_service.mapper;

import com.ecomerce.user_service.dto.request.RegisterRequest;
import com.ecomerce.user_service.entity.Role;
import com.ecomerce.user_service.entity.User;
import com.ecomerce.user_service.exception.DataNotFoundException;
import com.ecomerce.user_service.kafka.event.UserCreatedEvent;
import com.ecomerce.user_service.repository.RoleRepository;
import com.ecomerce.user_service.util.RoleDefineUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class UserMapper {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterRequest request) {
        if (request == null) return null;

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());

        Role role;
        if (request.getRole() == null) {
            role = roleRepository.findByName(RoleDefineUtil.CUSTOMER)
                    .orElseThrow(() -> new DataNotFoundException("Default role not defined"));
        } else {
            role = roleRepository.findByName(request.getRole())
                    .orElseThrow(() -> new DataNotFoundException("Role not found: " + request.getRole()));
        }
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    public UserCreatedEvent entityToUserCreatedEvent(User user) {
        return new UserCreatedEvent(
                user.getId(),
                user.getUuid(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getRole().getName(),
                ZonedDateTime.now()
        );
    }
}
