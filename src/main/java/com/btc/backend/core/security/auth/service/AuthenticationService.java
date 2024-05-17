package com.btc.backend.core.security.auth.service;

import com.btc.backend.core.security.auth.dto.AuthRequestDTO;
import com.btc.backend.core.security.auth.dto.AuthResponseDTO;
import com.btc.backend.core.security.auth.util.AuthResponseBuilder;
import com.btc.backend.core.security.jwt.service.JwtGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtGenerationService jwtGenerationService;
    private final AuthResponseBuilder authResponseBuilder;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtGenerationService jwtGenerationService,
                                 AuthResponseBuilder authResponseBuilder) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerationService = jwtGenerationService;
        this.authResponseBuilder = authResponseBuilder;
    }

    public ResponseEntity<AuthResponseDTO> authenticate(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword())
            );
        } catch (AuthenticationException e) {
            logger.warn("Failed to authenticate user - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String accessToken = jwtGenerationService.generateAccessToken(request.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(
                authResponseBuilder.build(accessToken));
    }
}