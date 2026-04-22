package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.input.AvaliarCreditoInput;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.LimiteCreditoExcedidoException;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ScoreInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostAvaliarCreditoClienteUseCaseImpl - Testes Unitários")
class PostAvaliarCreditoClienteUseCaseImplTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;

    @Mock
    private ScoreCalculatorService scoreCalculatorService;

    @Mock
    private SaldoCalculatorService saldoCalculatorService;

    @InjectMocks
    private PostAvaliarCreditoClienteUseCaseImpl useCase;

    private ClienteModel cliente;
    private AvaliarCreditoInput input;

    @BeforeEach
    void setUp() {
        cliente = new ClienteModel();
        cliente.setId("1");
        cliente.setNome("Cliente Teste");
        cliente.setSaldo(new BigDecimal("10000.00"));
        cliente.setScore(new BigDecimal("70"));
        cliente.setTransacoes(Collections.emptyList());

        input = new AvaliarCreditoInput(
        "1",
        new BigDecimal("3000.00")
        );
    }

    @Test
    @DisplayName("Deve aprovar crédito quando cliente atende todos os critérios")
    void deve_aprovarCredito_quando_criteriosAtendidos() {

        when(clienteRepositoryPort.buscarporId("1"))
                .thenReturn(Optional.of(cliente));

        when(scoreCalculatorService.calcularScore(any(ClienteModel.class)))
                .thenReturn(new BigDecimal("70"));

        when(saldoCalculatorService.calcularSaldo(any(ClienteModel.class)))
                .thenReturn(new BigDecimal("10000.00"));

        boolean aprovado = useCase.execute(input);

        assertTrue(aprovado);

        verify(clienteRepositoryPort).buscarporId("1");
        verify(scoreCalculatorService).calcularScore(cliente);
        verify(saldoCalculatorService).calcularSaldo(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    void deve_lancarExcecao_quando_clienteNaoEncontrado() {

        when(clienteRepositoryPort.buscarporId("1"))
                .thenReturn(Optional.empty());

        assertThrows(
                ClienteNaoEncontradoException.class,
                () -> useCase.execute(input)
        );

        verify(clienteRepositoryPort).buscarporId("1");
        verifyNoInteractions(scoreCalculatorService, saldoCalculatorService);
    }

    @Test
    @DisplayName("Deve lançar exceção quando valor solicitado exceder 35% do saldo")
    void deve_lancarExcecao_quando_limiteCreditoExcedido() {


        AvaliarCreditoInput input =
                new AvaliarCreditoInput("1", new BigDecimal("4000.00"));

        ClienteModel cliente = new ClienteModel();
        cliente.setId("1");
        cliente.setNome("Cliente Teste");
        cliente.setSaldo(new BigDecimal("10000.00"));
        cliente.setScore(new BigDecimal("70"));
        cliente.setTransacoes(Collections.emptyList());


        when(clienteRepositoryPort.buscarporId("1"))
                .thenReturn(Optional.of(cliente));

        when(scoreCalculatorService.calcularScore(cliente))
                .thenReturn(new BigDecimal("70"));

        when(saldoCalculatorService.calcularSaldo(cliente))
                .thenReturn(new BigDecimal("10000.00"));


        assertThrows(
                LimiteCreditoExcedidoException.class,
                () -> useCase.execute(input)
        );

    }

    @Test
    @DisplayName("Deve lançar exceção quando score for insuficiente")
    void deve_lancarExcecao_quando_scoreInsuficiente() {

        when(clienteRepositoryPort.buscarporId("1"))
                .thenReturn(Optional.of(cliente));

        when(scoreCalculatorService.calcularScore(cliente))
                .thenReturn(new BigDecimal("50"));

        when(saldoCalculatorService.calcularSaldo(cliente))
                .thenReturn(new BigDecimal("10000.00"));

        assertThrows(
                ScoreInsuficienteException.class,
                () -> useCase.execute(input)
        );
    }
}