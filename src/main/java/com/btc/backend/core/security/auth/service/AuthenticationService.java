package com.btc.backend.core.security.auth.service;

import com.btc.backend.app.account.api.AccountService;
import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.core.common.model.entity.Provider;
import com.btc.backend.core.common.model.enums.ProviderType;
import com.btc.backend.core.common.repository.ProviderRepository;
import com.btc.backend.core.security.auth.model.dto.AuthRequestDTO;
import com.btc.backend.core.security.auth.model.dto.AuthResponseDTO;
import com.btc.backend.core.security.auth.model.dto.RegisterRequestDTO;
import com.btc.backend.core.security.auth.model.dto.VerificationRequest;
import com.btc.backend.core.security.auth.util.AuthResponseBuilder;
import com.btc.backend.core.security.auth.util.GoogleTokenValidityProvider;
import com.btc.backend.core.security.jwt.service.JwtGenerationService;
import com.btc.backend.core.security.tfa.TwoFactorAuthenticationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtGenerationService jwtGenerationService,
                                 AuthResponseBuilder authResponseBuilder,
                                 GoogleTokenValidityProvider googleTokenValidityProvider,
                                 AccountService accountService,
                                 ProviderRepository providerRepository,
                                 TwoFactorAuthenticationService twoFactorAuthenticationService,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerationService = jwtGenerationService;
        this.authResponseBuilder = authResponseBuilder;
        this.googleTokenValidityProvider = googleTokenValidityProvider;
        this.accountService = accountService;
        this.providerRepository = providerRepository;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setTfaEnabled(request.isTfaEnabled());

        if (request.isTfaEnabled()) {
            account.setSecret(twoFactorAuthenticationService.generateNewSecret());
        }

        Account saved = accountService.save(account);
        String accessToken = jwtGenerationService.generateAccessToken(request.getUsername());
        long id = saved.getId();

        return ResponseEntity.status(HttpStatus.OK).body(
                authResponseBuilder.build(
                        accessToken,
                        id,
                        request.isTfaEnabled() ?
                                twoFactorAuthenticationService.generateQrCodeImageUri(account.getSecret()) : null,
                        request.isTfaEnabled()));
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

        Account account = accountService.findByUsername(request.getUsername()).orElseThrow();
        if (account.isTfaEnabled()) {
            AuthResponseDTO response = new AuthResponseDTO();
            response.setAccountId(account.getId());
            response.setAccessToken("");
            response.setTfaEnabled(account.isTfaEnabled());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                authResponseBuilder.build(accessToken, id, null, false));
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

                return ResponseEntity.status(HttpStatus.OK).body(authResponseBuilder.build(accessToken, id, null, account.get().isTfaEnabled()));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public ResponseEntity<AuthResponseDTO> verifyQrCode(VerificationRequest verificationRequest) {

        Account account = accountService.findByUsername(verificationRequest.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(String.format("No user found with %s", verificationRequest.getUsername()))
        );
        if (twoFactorAuthenticationService.isOtpNotValid(account.getSecret(), verificationRequest.getCode())) {
            throw new BadCredentialsException(("Code is not correct"));
        }

        String accessToken = jwtGenerationService.generateAccessToken(verificationRequest.getUsername());
        long id = account.getId();

        return ResponseEntity.ok(authResponseBuilder.build(accessToken, id, null, account.isTfaEnabled()));
    }

    public ResponseEntity<Boolean> findIsTfaEnabled(long id) {
        Account account = accountService.findById(id).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(account.isTfaEnabled());
    }

    public ResponseEntity<AuthResponseDTO> updateTfaSettings(long id, boolean tfa) {
        Account account = accountService.findById(id).orElseThrow(EntityNotFoundException::new);

        account.setTfaEnabled(tfa);
        accountService.save(account);
        AuthResponseDTO response = new AuthResponseDTO();

        if (tfa) {
            account.setSecret(twoFactorAuthenticationService.generateNewSecret());
            response.setSecretImageUri(twoFactorAuthenticationService.generateQrCodeImageUri(account.getSecret()));
            return ResponseEntity.ok(response);
        } else {
            account.setSecret(null);
        }

        return ResponseEntity.ok().build();
    }
}
