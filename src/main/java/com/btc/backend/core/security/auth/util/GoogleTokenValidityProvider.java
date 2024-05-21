package com.btc.backend.core.security.auth.util;

import com.btc.backend.core.security.auth.service.AuthenticationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleTokenValidityProvider {

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientId;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public Payload verifyAndExtract(String token) {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(token);
            return idToken.getPayload();
        } catch (IllegalArgumentException exception) {
            logger.error(exception.getMessage());
        } catch (GeneralSecurityException exception) {
            logger.error("Token unrecognized: {}", exception.getMessage());
        } catch (IOException exception) {
            logger.error("Exception occurred: {}", exception.getMessage());
        }
        return null;
    }
}
