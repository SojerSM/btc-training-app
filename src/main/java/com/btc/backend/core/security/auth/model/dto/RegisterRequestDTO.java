package com.btc.backend.core.security.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotNull
    private boolean tfaEnabled;

    public RegisterRequestDTO() {
        // empty
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTfaEnabled() {
        return tfaEnabled;
    }

    public void setTfaEnabled(boolean tfaEnabled) {
        this.tfaEnabled = tfaEnabled;
    }
}
