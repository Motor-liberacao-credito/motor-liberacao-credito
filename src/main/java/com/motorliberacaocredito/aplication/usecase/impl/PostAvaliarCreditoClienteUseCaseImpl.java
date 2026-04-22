package com.motorliberacaocredito.aplication.usecase.impl;

import com.motorliberacaocredito.aplication.usecase.PostAvaliarCreditoClienteUseCase;
import com.motorliberacaocredito.aplication.usecase.input.AvaliarCreditoInput;
import com.motorliberacaocredito.domain.model.ClienteModel;
import com.motorliberacaocredito.domain.port.ClienteRepositoryPort;
import com.motorliberacaocredito.domain.service.SaldoCalculatorService;
import com.motorliberacaocredito.domain.service.ScoreCalculatorService;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ClienteNaoEncontradoException;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.LimiteCreditoExcedidoException;
import com.motorliberacaocredito.infrastructure.entrypoint.exception.ScoreInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
//import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostAvaliarCreditoClienteUseCaseImpl implements PostAvaliarCreditoClienteUseCase
{
    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ScoreCalculatorService scoreCalculatorService;
    private final SaldoCalculatorService saldoCalculatorService;

    @Override
    public boolean execute(AvaliarCreditoInput input){

        ClienteModel cliente = clienteRepositoryPort.buscarporId(input.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));


        BigDecimal novoScore = scoreCalculatorService.calcularScore(cliente);
        BigDecimal novoSaldo = saldoCalculatorService.calcularSaldo(cliente);
        cliente.setSaldo(novoSaldo);


        BigDecimal limitePermitido = cliente.getSaldo().multiply(BigDecimal.valueOf(0.35));



        if (input.getValorSolicitado().compareTo(limitePermitido) > 0) {
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            throw new LimiteCreditoExcedidoException(
                    String.format(
                            "Valor solicitado (%s) excede o limite permitido (%s), correspondente a 35%% do saldo.",

                            nf.format(input.getValorSolicitado()),
                            nf.format(limitePermitido)

                    )
            );

        }


        if (novoScore.compareTo(BigDecimal.valueOf(60)) <= 0) {
            throw new ScoreInsuficienteException("Score insuficiente para aprovação de crédito");
        }


        return true;

    }


}
