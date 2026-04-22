package com.motorliberacaocredito.infrastructure.entrypoint.handler;

import com.motorliberacaocredito.domain.model.ErrorResponse;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.LimiteCreditoExcedidoException;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ScoreInsuficienteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =======================
       404 – Cliente não encontrado
       ======================= */
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleClienteNaoEncontrado(
            ClienteNaoEncontradoException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    /* =======================
       422 – Regras de negócio
       ======================= */
    @ExceptionHandler({
            LimiteCreditoExcedidoException.class,
            ScoreInsuficienteException.class
    })
    public ResponseEntity<ErrorResponse> handleRegraNegocio(
            RuntimeException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                request
        );
    }

    /* =======================
       500 – Erro inesperado
       ======================= */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleErroInesperado(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Unexpected server error")
                .path(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    /* =======================
       Método utilitário CENTRAL
       ======================= */
    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(message)
                .path(request.getRequestURI());

        return ResponseEntity
                .status(status)
                .body(error);
    }
}