package com.ecomerce.user_service.service.auth;

import com.ecomerce.user_service.dto.request.LoginRequest;
import com.ecomerce.user_service.dto.request.RegisterRequest;
import jakarta.validation.Valid;

public interface IAuthService {
    void registerUser(RegisterRequest request);
}
