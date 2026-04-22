package com.motorliberacaocredito.infrastructure.entrypoint.exception;

public class ScoreInsuficienteException extends RuntimeException {
    public ScoreInsuficienteException(String message) {
        super(message);
    }
}