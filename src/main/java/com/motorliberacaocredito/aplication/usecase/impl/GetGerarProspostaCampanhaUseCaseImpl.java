package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.GetGerarProspostaCampanhaUseCase;
import com.motorliberacaocredito.domain.model.PropostaCampanhaModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GetGerarProspostaCampanhaUseCaseImpl implements GetGerarProspostaCampanhaUseCase {

    // Saldo e Score
    // Calcular probabilidade
    private final ClienteRepositoryPort clienteRepositoryPort;
    private final SaldoCalculatorService saldoCalculatorService;

    @Override
    public PropostaCampanhaModel execute(String id){


     return clienteRepositoryPort.buscarporId(id)
             .map(cliente -> {

                 BigDecimal saldo = saldoCalculatorService.calcularSaldo(cliente);
                 cliente.setSaldo(saldo);
                 return cliente;
             })
             .filter(cliente -> cliente.getSaldo().compareTo(BigDecimal.ZERO) < 0)
             .map(cliente ->
             {
                 BigDecimal saldoNegativo = cliente.getSaldo();
                 BigDecimal saldoPositivo = saldoNegativo.abs();
                 BigDecimal valorParcela = saldoPositivo.multiply(BigDecimal.valueOf(0.10));

                 String mensagem = String.format(
                         "Olá %s, você pode sair da inadimplência com parcelas de até R$ %s por mês.",
                         cliente.getNome(),
                         valorParcela.setScale(2, RoundingMode.HALF_UP)
                 );

                 return new PropostaCampanhaModel(
                         cliente.getId(),
                         cliente.getNome(),
                         mensagem
                 );

     })
             .orElseThrow(() -> new RuntimeException(id));

    }
}
