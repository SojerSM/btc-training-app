package com.btc.backend.core.security.auth.dto;

import java.time.LocalDateTime;

public class AuthResponseDTO {

    private String accessToken;
    private LocalDateTime expireAt;

    public AuthResponseDTO() {
        //empty constructor
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }
}
