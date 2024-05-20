package com.btc.backend.core.security.filters;

import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.app.account.core.repository.AccountRepository;
import com.btc.backend.core.security.auth.service.AuthenticationService;
import com.btc.backend.core.security.jwt.util.AuthPropertiesProvider;
import com.btc.backend.core.security.jwt.util.JwtDetailsProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final JwtDetailsProvider jwtDetailsProvider;
    private final AuthPropertiesProvider authPropertiesProvider;
    private final AccountRepository accountRepository;

    public JwtAuthenticationFilter(AuthPropertiesProvider authPropertiesProvider,
                                   JwtDetailsProvider jwtDetailsProvider,
                                   AccountRepository accountRepository) {
        this.authPropertiesProvider = authPropertiesProvider;
        this.jwtDetailsProvider = jwtDetailsProvider;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(authPropertiesProvider.getAuthHeader());
        final String token;
        final String username;

        logger.warn("Filtering");

        if (authHeader == null || !authHeader.startsWith(authPropertiesProvider.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(authPropertiesProvider.getPrefix().length() + 1);
        username = jwtDetailsProvider.extractUsername(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null && jwtDetailsProvider.isTokenValid(token)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, null);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        logger.warn("Authenticated: ".concat(String.valueOf(SecurityContextHolder.getContext().getAuthentication().isAuthenticated())));

        filterChain.doFilter(request, response);
    }
}