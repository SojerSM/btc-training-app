package com.btc.backend.core.security.jwt.service;

import com.btc.backend.core.security.jwt.util.AuthPropertiesProvider;
import com.btc.backend.core.security.jwt.util.JwtDetailsProvider;
import org.springframework.stereotype.Service;

@Service
public class JwtGenerationService {

    private final AuthPropertiesProvider authPropertiesProvider;
    private final JwtDetailsProvider jwtDetailsProvider;

    public JwtGenerationService(AuthPropertiesProvider authPropertiesProvider,
                                JwtDetailsProvider jwtDetailsProvider) {
        this.authPropertiesProvider = authPropertiesProvider;
        this.jwtDetailsProvider = jwtDetailsProvider;
    }

    public String generateAccessToken(String username) {
        String delay = authPropertiesProvider.getAccessExpDelay();
        return jwtDetailsProvider.generateToken(username, Long.parseLong(delay));
    }
}