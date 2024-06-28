package com.btc.backend.app.account.core.model.entity;

import com.btc.backend.core.common.model.entity.Provider;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    private boolean tfaEnabled;
    private String secret;

    @ManyToMany
    @JoinTable(
            name = "account_provider",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "provider_id")
    )
    private List<Provider> allowedAuthProviders;


    public Account() {
        // empty controller
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Provider> getAllowedAuthProviders() {
        return allowedAuthProviders;
    }

    public void setAllowedAuthProviders(List<Provider> allowedAuthProviders) {
        this.allowedAuthProviders = allowedAuthProviders;
    }

    public boolean isTfaEnabled() {
        return tfaEnabled;
    }

    public void setTfaEnabled(boolean tfaEnabled) {
        this.tfaEnabled = tfaEnabled;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
