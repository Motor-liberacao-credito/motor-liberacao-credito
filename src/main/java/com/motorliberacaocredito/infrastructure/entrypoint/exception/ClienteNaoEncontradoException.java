package com.motorliberacaocredito.infrastructure.entrypoint.exception;


public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}

