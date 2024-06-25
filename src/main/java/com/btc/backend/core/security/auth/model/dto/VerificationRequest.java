package com.btc.backend.core.security.auth.model.dto;

public class VerificationRequest {
    private String username;
    private String code;

    public VerificationRequest() {
        // empty constructor
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
