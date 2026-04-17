package com.motorliberacaocredito.infrastructure.entrypoint.exception;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }

    public ClienteNaoEncontradoException(String message, RuntimeException rte) {
        super(message, rte);
    }
}

// Saldo Insuficiente e score