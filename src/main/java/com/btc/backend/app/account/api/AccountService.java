package com.btc.backend.app.account.api;

import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.core.security.auth.model.dto.RegisterRequestDTO;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(long id);

    Account save(Account account);

    String findUsernameById(long id);
}
