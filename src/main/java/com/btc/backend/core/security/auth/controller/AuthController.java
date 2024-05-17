package com.btc.backend.core.security.auth.controller;

import com.btc.backend.core.rest.RestEndpoints;
import com.btc.backend.core.security.auth.dto.AuthRequestDTO;
import com.btc.backend.core.security.auth.dto.AuthResponseDTO;
import com.btc.backend.core.security.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RestEndpoints.AUTH_PATH)
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody @Validated AuthRequestDTO request) {
        return authenticationService.authenticate(request);
    }
}
