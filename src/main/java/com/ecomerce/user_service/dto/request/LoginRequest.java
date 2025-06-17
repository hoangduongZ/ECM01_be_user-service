package com.ecomerce.user_service.dto.request;


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "user name cannot be blank!")
    private String username;
    @NotBlank(message = "password cannot be blank!")
//    validate by REGEX
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
