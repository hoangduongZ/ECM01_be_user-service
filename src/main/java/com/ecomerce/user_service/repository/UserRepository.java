package com.ecomerce.user_service.repository;

import com.ecomerce.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUuid(UUID id);
}
