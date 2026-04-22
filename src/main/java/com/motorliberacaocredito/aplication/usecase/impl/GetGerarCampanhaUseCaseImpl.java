package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.GetGerarCampanhaUseCase;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.CampanhaService;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetGerarCampanhaUseCaseImpl implements GetGerarCampanhaUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final CampanhaService campanhaService;
    private final ScoreCalculatorService scoreCalculatorService;
    private final SaldoCalculatorService saldoCalculatorService;

    @Override
    public PropostaCampanhaModel gerarCampanha(String clienteId) {

        ClienteModel cliente = clienteRepositoryPort.buscarporId(clienteId)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));


        BigDecimal scoreCalculado =
                scoreCalculatorService.calcularScore(cliente);
        cliente.setScore(scoreCalculado);


        BigDecimal saldoCalculado =
                saldoCalculatorService.calcularSaldo(cliente);
        cliente.setSaldo(saldoCalculado);


        return campanhaService.gerarProposta(cliente);

    }
}
