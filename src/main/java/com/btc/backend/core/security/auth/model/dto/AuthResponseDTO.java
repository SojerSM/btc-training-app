package com.btc.backend.core.security.auth.model.dto;

import java.time.LocalDateTime;

public class AuthResponseDTO {

    private String accessToken;
    private LocalDateTime expireAt;
    private long accountId;

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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
