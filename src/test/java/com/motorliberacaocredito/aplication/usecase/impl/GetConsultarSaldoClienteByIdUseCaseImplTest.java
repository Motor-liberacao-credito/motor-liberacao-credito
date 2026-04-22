package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConsultarClienteUseCase - Testes Unitários")
class GetConsultarSaldoClienteByIdUseCaseImplTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;


    @Mock
    private ScoreCalculatorService scoreCalculatorService;

    @Mock
    private SaldoCalculatorService saldoCalculatorService;


    @InjectMocks
    private GetConsultarSaldoClienteByIdUseCaseImpl getConsultarSaldoClienteByIdUseCase;

    private ClienteModel clienteMock;

    @BeforeEach
    void setUp(){
        clienteMock = new ClienteModel();
                clienteMock.setId("1");
                clienteMock.setNome("Cliente_1");
                clienteMock.setSaldo(BigDecimal.valueOf(3500.75)) ;
                clienteMock.setScore(BigDecimal.valueOf(68));
                clienteMock.setTransacoes(Collections.emptyList());

    }

    @Test
    @DisplayName("Deve retornar cliente com saldo e score quando ID válido")
    void deve_retornarCliente_quando_idValido() {

        when(clienteRepositoryPort.buscarporId("1"))
                .thenReturn(Optional.of(clienteMock));

        when(scoreCalculatorService.calcularScore(any(ClienteModel.class)))
                .thenReturn(new BigDecimal("70"));

        when(saldoCalculatorService.calcularSaldo(any(ClienteModel.class)))
                .thenReturn(new BigDecimal("4000.00"));

        // Act
        Optional<ClienteModel> resultado =
                getConsultarSaldoClienteByIdUseCase.execute("1");

        // Assert
        assertTrue(resultado.isPresent());

        ClienteModel cliente = resultado.get();
        assertEquals("1", cliente.getId());
        assertEquals("Cliente_1", cliente.getNome());
        assertEquals(new BigDecimal("70"), cliente.getScore());
        assertEquals(new BigDecimal("4000.00"), cliente.getSaldo());

        verify(clienteRepositoryPort).buscarporId("1");
        verify(scoreCalculatorService).calcularScore(clienteMock);
        verify(saldoCalculatorService).calcularSaldo(clienteMock);

    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando cliente não encontrado")
    void deve_lancarExcecao_quando_clienteNaoEncontrado() {
        // Arrange
        when(clienteRepositoryPort.buscarporId("99"))
                .thenReturn(Optional.empty());

        // Act & Assert
        ClienteNaoEncontradoException excecao = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> getConsultarSaldoClienteByIdUseCase.execute("99")
        );

        assertEquals("Cliente não encontrado para o id: 99", excecao.getMessage()
        );

    }
}