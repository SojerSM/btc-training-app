package com.btc.backend.core.security.jwt.util;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class AuthPropertiesProvider {

    private String secretKey;
    private String accessExpDelay;
    private String prefix;
    private String authHeader;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessExpDelay() {
        return accessExpDelay;
    }

    public void setAccessExpDelay(String accessExpDelay) {
        this.accessExpDelay = accessExpDelay;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }
}
