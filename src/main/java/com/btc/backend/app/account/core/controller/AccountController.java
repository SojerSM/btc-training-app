package com.btc.backend.app.account.core.controller;

import com.btc.backend.app.account.api.AccountService;
import com.btc.backend.app.account.core.model.dto.AccountResponseDTO;
import com.btc.backend.core.rest.RestEndpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(RestEndpoints.ACCOUNT_PATH)
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}/username")
    public ResponseEntity<Map<String,String>> findById(@PathVariable(name = "id") long id) {
        String username = accountService.findUsernameById(id);
        Map<String,String> response = new HashMap<>();
        response.put("username",username);
        return ResponseEntity.ok(response);
    }
}
