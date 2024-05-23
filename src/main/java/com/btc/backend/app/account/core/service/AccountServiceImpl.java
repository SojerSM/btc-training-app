package com.btc.backend.app.account.core.service;

import com.btc.backend.app.account.api.AccountService;
import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.app.account.core.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }
}
