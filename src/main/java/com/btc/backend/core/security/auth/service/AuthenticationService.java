package com.btc.backend.core.security.auth.service;

import com.btc.backend.app.account.api.AccountService;
import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.core.common.model.entity.Provider;
import com.btc.backend.core.common.repository.ProviderRepository;
import com.btc.backend.core.security.auth.model.dto.AuthRequestDTO;
import com.btc.backend.core.security.auth.model.dto.AuthResponseDTO;
import com.btc.backend.core.common.model.enums.ProviderType;
import com.btc.backend.core.security.auth.util.AuthResponseBuilder;
import com.btc.backend.core.security.auth.util.GoogleTokenValidityProvider;
import com.btc.backend.core.security.jwt.service.JwtGenerationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtGenerationService jwtGenerationService;
    private final AuthResponseBuilder authResponseBuilder;
    private final GoogleTokenValidityProvider googleTokenValidityProvider;
    private final AccountService accountService;
    private final ProviderRepository providerRepository;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtGenerationService jwtGenerationService,
                                 AuthResponseBuilder authResponseBuilder,
                                 GoogleTokenValidityProvider googleTokenValidityProvider,
                                 AccountService accountService,
                                 ProviderRepository providerRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerationService = jwtGenerationService;
        this.authResponseBuilder = authResponseBuilder;
        this.googleTokenValidityProvider = googleTokenValidityProvider;
        this.accountService = accountService;
        this.providerRepository = providerRepository;
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
        long id = accountService.findByUsername(request.getUsername()).orElseThrow().getId();

        return ResponseEntity.status(HttpStatus.OK).body(
                authResponseBuilder.build(accessToken, id));
    }

    public ResponseEntity<AuthResponseDTO> verifyWithProvider(String provider, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (ProviderType.valueOf(provider.toUpperCase()) == ProviderType.GOOGLE) {
            Payload payload = googleTokenValidityProvider.verifyAndExtract(token);
            Optional<Account> account = accountService.findAccountByEmail(payload.getEmail());
            Optional<Provider> googleProvider = providerRepository.findById(2);

            if (account.isPresent() && googleProvider.isPresent() &&
                    account.get().getAllowedAuthProviders().contains(googleProvider.get())) {
                String accessToken = jwtGenerationService.generateAccessToken(account.get().getUsername());
                long id = account.get().getId();

                return ResponseEntity.status(HttpStatus.OK).body(authResponseBuilder.build(accessToken, id));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}