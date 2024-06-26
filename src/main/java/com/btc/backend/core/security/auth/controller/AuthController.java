package com.btc.backend.core.security.auth.controller;

import com.btc.backend.core.rest.RestEndpoints;
import com.btc.backend.core.security.auth.model.dto.AuthRequestDTO;
import com.btc.backend.core.security.auth.model.dto.AuthResponseDTO;
import com.btc.backend.core.security.auth.model.dto.RegisterRequestDTO;
import com.btc.backend.core.security.auth.model.dto.VerificationRequest;
import com.btc.backend.core.security.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = RestEndpoints.AUTH_PATH)
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "/{id}/tfa")
    public ResponseEntity<Map<String, Boolean>> findIsTfaEnabled(@PathVariable(name = "id") long id) {
        return authenticationService.findIsTfaEnabled(id);
    }

    @PutMapping(value = "/{id}/tfa")
    public ResponseEntity<AuthResponseDTO> updateTfaSettings(@PathVariable(name = "id") long id, @RequestBody boolean tfa) {

        return authenticationService.updateTfaSettings(id,tfa);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return authenticationService.register(request);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody @Validated AuthRequestDTO request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping(value = "/verifyWithProvider")
    public ResponseEntity<AuthResponseDTO> verifyWithProvider(@RequestParam(value = "provider") String provider,
                                                              HttpServletRequest request) {
        return authenticationService.verifyWithProvider(provider, request);
    }

    @GetMapping(value = "/checkAuth")
    public ResponseEntity<?> checkAuth() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifyQr")
    public ResponseEntity<?> verifyQrCode(@RequestBody VerificationRequest verificationRequest) {
        return ResponseEntity.ok(authenticationService.verifyQrCode(verificationRequest));
    }

    @PostMapping("/generateQr")
    public ResponseEntity<?> generateQr() {
        return ResponseEntity.ok(authenticationService.generateQr());
    }
}
