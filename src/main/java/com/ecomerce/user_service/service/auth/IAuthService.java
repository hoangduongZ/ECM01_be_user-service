package com.ecomerce.user_service.service.auth;

import com.ecomerce.user_service.dto.request.RegisterRequest;

import java.util.UUID;

public interface IAuthService {
    void registerUser(RegisterRequest request);
    void deactivateUser(UUID uuid);
}