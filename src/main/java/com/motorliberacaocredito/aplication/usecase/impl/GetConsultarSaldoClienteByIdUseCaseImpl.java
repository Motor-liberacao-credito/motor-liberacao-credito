package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.GetConsultarSaldoClienteByIdUseCase;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetConsultarSaldoClienteByIdUseCaseImpl implements GetConsultarSaldoClienteByIdUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ScoreCalculatorService scoreCalculatorService;
    private final SaldoCalculatorService saldoCalculatorService;

    @Override
    public Optional<ClienteModel> execute(String id){

        ClienteModel cliente = clienteRepositoryPort.buscarporId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado para o id: " + id));


            BigDecimal novoScore = scoreCalculatorService.calcularScore(cliente);
            cliente.setScore(novoScore);

            BigDecimal novoSaldo = saldoCalculatorService.calcularSaldo(cliente);
            cliente.setSaldo(novoSaldo);
            return Optional.of(cliente);

    }

}
