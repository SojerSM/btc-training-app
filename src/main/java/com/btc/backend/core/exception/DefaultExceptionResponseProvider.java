package com.btc.backend.core.exception;

import com.btc.backend.core.common.dto.CustomExceptionResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultExceptionResponseProvider {

    public CustomExceptionResponseDTO generate(String message) {
        return new CustomExceptionResponseDTO(message, LocalDateTime.now());
    }
}