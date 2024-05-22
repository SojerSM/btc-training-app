package com.btc.backend.app.account.api;

import com.btc.backend.app.account.core.model.entity.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findByUsername(String username);
}
