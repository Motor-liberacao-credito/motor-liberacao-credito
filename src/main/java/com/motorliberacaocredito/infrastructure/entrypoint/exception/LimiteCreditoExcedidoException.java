package com.motorliberacaocredito.infrastructure.entrypoint.exception;

public class LimiteCreditoExcedidoException extends RuntimeException {
    public LimiteCreditoExcedidoException(String message) {
        super(message);
    }
}