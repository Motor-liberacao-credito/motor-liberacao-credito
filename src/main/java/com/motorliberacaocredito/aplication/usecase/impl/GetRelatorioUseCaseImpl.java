package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.GetRelatorioUseCase;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.model.RelatorioModel;
import com.motorliberacaocredito.domain.port.RelatorioRepositoryPort;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GetRelatorioUseCaseImpl implements GetRelatorioUseCase {

    private final RelatorioRepositoryPort relatorioRepositoryPort ;
    private final ScoreCalculatorService scoreCalculatorService;
    private final SaldoCalculatorService saldoCalculatorService;

    @Override
    public List<RelatorioModel> execute(){
        return relatorioRepositoryPort.buscarTodos()
                .stream()
                .map(this::calcularDadosCliente)
                .filter(cliente -> cliente.getSaldo().compareTo(BigDecimal.ZERO) < 0)
                .map(this::toRelatorioModel)
                .toList();

    }

    private ClienteModel calcularDadosCliente(ClienteModel cliente) {
        BigDecimal saldo = saldoCalculatorService.calcularSaldo(cliente);
        BigDecimal score = scoreCalculatorService.calcularScore(cliente);
        cliente.setSaldo(saldo);
        cliente.setScore(score);
        return cliente;
    }



    private RelatorioModel toRelatorioModel(ClienteModel cliente) {

        BigDecimal saldoPositivo = cliente.getSaldo().abs();
        BigDecimal valorParcela =
                saldoPositivo.multiply(BigDecimal.valueOf(0.10))
                        .setScale(2, RoundingMode.HALF_UP);

        String mensagem = String.format(
                "Olá %s, você pode sair da inadimplência com parcelas de até R$ %s por mês.",
                cliente.getNome(),
                valorParcela
        );

        return new RelatorioModel(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSaldo(),
                cliente.getScore(),
                mensagem
        );
    }

}

