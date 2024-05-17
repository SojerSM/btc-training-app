package com.btc.backend.core.security.auth.util;

import com.btc.backend.core.security.auth.dto.AuthResponseDTO;
import com.btc.backend.core.security.jwt.util.AuthPropertiesProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AuthResponseBuilder {

    private final AuthPropertiesProvider authPropertiesProvider;

    public AuthResponseBuilder(AuthPropertiesProvider authPropertiesProvider) {
        this.authPropertiesProvider = authPropertiesProvider;
    }

    public AuthResponseDTO build(String accessToken) {
        AuthResponseDTO response = new AuthResponseDTO();
        response.setAccessToken(accessToken);
        response.setExpireAt(LocalDateTime.now().plus(
                Long.parseLong(authPropertiesProvider.getAccessExpDelay()), ChronoUnit.MILLIS));

        return response;
    }
}